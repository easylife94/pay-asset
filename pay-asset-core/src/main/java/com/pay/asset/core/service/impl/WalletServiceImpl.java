package com.pay.asset.core.service.impl;

import com.pay.asset.client.constants.SpringBeanNamespaces;
import com.pay.asset.client.constants.WalletOwnRoleEnum;
import com.pay.asset.client.dto.WalletRecordDTO;
import com.pay.asset.client.dto.WalletSubRecordDTO;
import com.pay.asset.client.model.AbstractWalletDetailBaseDO;
import com.pay.asset.client.model.WalletDO;
import com.pay.asset.client.model.WalletDetailAgentDO;
import com.pay.asset.client.model.WalletDetailMemberDO;
import com.pay.asset.core.dao.WalletDao;
import com.pay.asset.core.dao.WalletDetailAgentDao;
import com.pay.asset.core.dao.WalletDetailMemberDao;
import com.pay.asset.core.service.IUniqueWalletRecordService;
import com.pay.asset.core.service.IWalletDetailService;
import com.pay.asset.core.service.IWalletService;
import com.pay.asset.core.utils.SpringContextUtil;
import com.pay.asset.core.wallet.IWalletDetailRecordHandle;
import com.pay.common.core.service.IDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包记录是针对订单，当订单状态发生变化才会发生钱包的变化
 * 例如：
 * 1.交易
 * 交易订单支付成功，会员钱包支出服务费。
 * 交易订单退款成功，会员钱包支出服务费退款。
 * 交易订单结算成功，代理商钱包收入服务费分润和通道分润。
 * 2.充值
 * 充值订单支付成功，会员钱包收入充值金额。
 * 充值订单退款成功，会员钱包支出充值金额退款。
 * 3.
 *
 * @author chenwei
 * @date 2019/4/17 14:17
 */
@Slf4j
@Service
public class WalletServiceImpl implements IWalletService {

    private final IDistributedLockService distributedLockService;
    private final IUniqueWalletRecordService uniqueWalletRecordService;
    private final WalletDao walletDao;
    private final WalletDetailAgentDao walletDetailAgentDao;
    private final WalletDetailMemberDao walletDetailMemberDao;
    private final IWalletDetailService walletDetailService;

    @Autowired
    public WalletServiceImpl(IDistributedLockService distributedLockService, IUniqueWalletRecordService uniqueWalletRecordService,
                             WalletDao walletDao, WalletDetailAgentDao walletDetailAgentDao, WalletDetailMemberDao walletDetailMemberDao, IWalletDetailService walletDetailService) {
        this.distributedLockService = distributedLockService;
        this.uniqueWalletRecordService = uniqueWalletRecordService;
        this.walletDao = walletDao;
        this.walletDetailAgentDao = walletDetailAgentDao;
        this.walletDetailMemberDao = walletDetailMemberDao;
        this.walletDetailService = walletDetailService;
    }


    /**
     * 订单状态变化才会触发钱包变化
     *
     * @param walletRecordDTO 钱包记录参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void walletRecord(WalletRecordDTO walletRecordDTO) {
        if (walletRecordDTO.getSubRecords().size() <= 0) {
            log.error("钱包记录失败，子记录数小于0：{}", walletRecordDTO);
            return;
        }
        //去重校验
        if (uniqueWalletRecordService.existed(walletRecordDTO)) {
            log.info("钱包记录操作去重：{}", walletRecordDTO);
            return;
        }
        //对同一个钱包和钱包详情的变化要加锁
        String lockKey = walletRecordDTO.getOwnRole().name() + walletRecordDTO.getOwnNumber();
        try {
            distributedLockService.lock(lockKey);
            WalletDO walletDO = walletDao.selectByOwner(walletRecordDTO.getOwnNumber(), walletRecordDTO.getOwnRole().name());
            if (walletDO == null) {
                log.error("钱包记录失败，钱包不存在，编号:{},角色：{}", walletRecordDTO.getOwnNumber(), walletRecordDTO.getOwnRole());
                return;
            }
            walletRecord(walletDO, walletRecordDTO.getSubRecords());
            walletDetailRecord(walletRecordDTO.getOwnRole(), walletDO.getId(), walletRecordDTO.getSubRecords());
            //todo 4.插入钱包子记录
            uniqueWalletRecordService.insert(walletRecordDTO);
        } finally {
            distributedLockService.unlock(lockKey);
        }
    }

    /**
     * 钱包表变化
     *
     * @param walletDO   钱包
     * @param subRecords 子记录
     */
    private void walletRecord(WalletDO walletDO, List<WalletSubRecordDTO> subRecords) {
        for (WalletSubRecordDTO subRecord : subRecords) {
            BigDecimal balanceTotal = walletDO.getBalanceTotal();
            BigDecimal balanceFrozen = walletDO.getBalanceFrozen();
            BigDecimal balanceUsable = walletDO.getBalanceUsable();
            BigDecimal expenditureTotal = walletDO.getExpenditureTotal();
            BigDecimal incomeTotal = walletDO.getIncomeTotal();
            switch (subRecord.getPaymentType()) {
                case IN:
                    balanceTotal = balanceTotal.add(subRecord.getAmount());
                    balanceUsable = balanceUsable.add(subRecord.getAmount());
                    incomeTotal = incomeTotal.add(subRecord.getAmount());
                    break;
                case OUT:
                    balanceTotal = balanceTotal.subtract(subRecord.getAmount());
                    balanceUsable = balanceUsable.subtract(subRecord.getAmount());
                    expenditureTotal = expenditureTotal.add(subRecord.getAmount());
                    break;
                case FROZEN:
                    balanceFrozen = balanceFrozen.add(subRecord.getAmount());
                    balanceUsable = balanceUsable.subtract(subRecord.getAmount());
                    break;
                case UNFROZEN:
                    balanceUsable = balanceUsable.add(subRecord.getAmount());
                    balanceFrozen = balanceFrozen.subtract(subRecord.getAmount());
                case IN_FROZEN:
                    balanceTotal = balanceTotal.add(subRecord.getAmount());
                    balanceFrozen = balanceFrozen.add(subRecord.getAmount());
                    incomeTotal = incomeTotal.add(subRecord.getAmount());
                    break;
                case OUT_UNFROZEN:
                    balanceTotal = balanceTotal.subtract(subRecord.getAmount());
                    balanceFrozen = balanceFrozen.subtract(subRecord.getAmount());
                    expenditureTotal = expenditureTotal.add(subRecord.getAmount());
                    break;
                default:
                    throw new RuntimeException("不支持钱包记录收支类型：" + subRecord.getPaymentType());
            }
            walletDO.setBalanceTotal(balanceTotal);
            walletDO.setBalanceUsable(balanceUsable);
            walletDO.setBalanceFrozen(balanceFrozen);
            walletDO.setIncomeTotal(incomeTotal);
            walletDO.setExpenditureTotal(expenditureTotal);
        }
        log.info("钱包记录后：{}", walletDO);
        walletDao.updateByPrimaryKeySelective(walletDO);
    }

    /**
     * 钱包详情记录
     *
     * @param ownRole    钱包拥有者
     * @param walletId   钱包id
     * @param subRecords 子记录
     */
    private void walletDetailRecord(WalletOwnRoleEnum ownRole, Long walletId, List<WalletSubRecordDTO> subRecords) {
        IWalletDetailRecordHandle walletDetailRecordHandle = (IWalletDetailRecordHandle) SpringContextUtil.getBean(SpringBeanNamespaces.WALLET_DETAIL_HANDLE + ownRole.name());
        AbstractWalletDetailBaseDO walletDetailBaseDO = walletDetailService.selectByWalletId(ownRole, walletId);
        for (WalletSubRecordDTO subRecord : subRecords) {
            walletDetailRecordHandle.record(walletDetailBaseDO, subRecord.getTradeType(), subRecord.getAmount());
        }
        log.info("钱包详情记录后：{}", walletDetailBaseDO);
        walletDetailService.update(ownRole, walletDetailBaseDO);
    }

}
