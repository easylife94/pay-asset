package com.pay.asset.core.service.impl;

import com.pay.asset.client.constants.SpringBeanNamespaces;
import com.pay.asset.client.constants.WalletOwnRoleEnum;
import com.pay.asset.client.dto.WalletRecordDTO;
import com.pay.asset.client.dto.WalletSubRecordDTO;
import com.pay.asset.client.model.AbstractWalletDetailBaseDO;
import com.pay.asset.client.model.WalletDO;
import com.pay.asset.client.model.WalletRecordDO;
import com.pay.asset.core.dao.WalletDao;
import com.pay.asset.core.dao.WalletRecordDao;
import com.pay.asset.core.service.IUniqueWalletRecordService;
import com.pay.asset.core.service.IWalletDetailService;
import com.pay.asset.core.service.IWalletService;
import com.pay.asset.core.utils.SpringContextUtil;
import com.pay.asset.core.wallet.IWalletDetailRecordHandle;
import com.pay.common.core.service.IDistributedLockService;
import com.pay.common.core.service.IIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * 3.提现
 * 提现订单下单成功（提交成功），会员钱包冻结提现金额
 * 提现订单支付成功（审核成功），会员钱包解冻支出提现金额
 * 提现订单支付失败（审核失败），会员钱包解冻提现金额
 *
 * @author chenwei
 * @date 2019/4/17 14:17
 */
@Slf4j
@Service
public class WalletServiceImpl implements IWalletService {

    @Value("${wallet.record.serial-number-prefix}")
    private String subRecordSerialNumberPrefix;

    private final IIdService idService;
    private final IDistributedLockService distributedLockService;
    private final IUniqueWalletRecordService uniqueWalletRecordService;
    private final IWalletDetailService walletDetailService;
    private final WalletDao walletDao;
    private final WalletRecordDao walletRecordDao;

    @Autowired
    public WalletServiceImpl(IIdService idService, IDistributedLockService distributedLockService, IUniqueWalletRecordService uniqueWalletRecordService,
                             IWalletDetailService walletDetailService, WalletDao walletDao, WalletRecordDao walletRecordDao) {
        this.idService = idService;
        this.distributedLockService = distributedLockService;
        this.uniqueWalletRecordService = uniqueWalletRecordService;
        this.walletDetailService = walletDetailService;
        this.walletDao = walletDao;
        this.walletRecordDao = walletRecordDao;
    }


    /**
     * 订单状态变化才会触发钱包变化
     * 对同一个钱包的变化加了分布式锁控制并发
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
        //对同一个钱包和钱包详情的变化要加锁
        String lockKey = walletRecordDTO.getOwnRole().name() + walletRecordDTO.getOwnNumber();
        try {
            distributedLockService.lock(lockKey);
            //去重校验
            if (uniqueWalletRecordService.existed(walletRecordDTO)) {
                log.info("钱包记录操作去重：{}", walletRecordDTO);
                return;
            }
            WalletDO walletDO = walletDao.selectByOwner(walletRecordDTO.getOwnNumber(), walletRecordDTO.getOwnRole().name());
            if (walletDO == null) {
                log.error("钱包记录失败，钱包不存在，编号:{},角色：{}", walletRecordDTO.getOwnNumber(), walletRecordDTO.getOwnRole());
                return;
            }
            walletRecord(walletDO, walletRecordDTO);
            walletDetailRecord(walletRecordDTO.getOwnRole(), walletDO.getId(), walletRecordDTO.getSubRecords());
            uniqueWalletRecordService.insert(walletRecordDTO);
        } finally {
            distributedLockService.unlock(lockKey);
        }
    }

    /**
     * 钱包表变化、钱包子记录生成
     *
     * @param walletDO        钱包
     * @param walletRecordDTO 记录
     */
    private void walletRecord(WalletDO walletDO, WalletRecordDTO walletRecordDTO) {
        BigDecimal balanceTotal = walletDO.getBalanceTotal();
        BigDecimal balanceFrozen = walletDO.getBalanceFrozen();
        BigDecimal balanceUsable = walletDO.getBalanceUsable();
        BigDecimal expenditureTotal = walletDO.getExpenditureTotal();
        BigDecimal incomeTotal = walletDO.getIncomeTotal();

        for (WalletSubRecordDTO subRecord : walletRecordDTO.getSubRecords()) {
            //子钱包记录
            WalletRecordDO walletRecordDO = new WalletRecordDO(idService.generateId());
            walletRecordDO.setWalletId(walletDO.getId());
            walletRecordDO.setOrderType(walletRecordDTO.getOrderType().name());
            walletRecordDO.setOrderNumber(walletRecordDTO.getOrderNumber());
            walletRecordDO.setOrderStatus(walletRecordDTO.getOrderStatus().name());
            walletRecordDO.setPaymentType(subRecord.getPaymentType().name());
            walletRecordDO.setTradeType(subRecord.getTradeType().name());
            walletRecordDO.setTradeTime(subRecord.getTradeTime());
            walletRecordDO.setTradeAmount(subRecord.getAmount());
            walletRecordDO.setSerialNumber(walletSubRecordSerialNumber());

            //变化前
            walletRecordDO.setBalanceTotalBefore(balanceTotal);
            walletRecordDO.setBalanceUsableBefore(balanceUsable);
            walletRecordDO.setBalanceFrozenBefore(balanceFrozen);
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
            //变化后
            walletRecordDO.setBalanceTotalAfter(balanceTotal);
            walletRecordDO.setBalanceUsableAfter(balanceUsable);
            walletRecordDO.setBalanceFrozenAfter(balanceFrozen);
            log.info("钱包子记录后：{}", walletRecordDO);
            walletRecordDao.insert(walletRecordDO);
        }
        walletDO.setBalanceTotal(balanceTotal);
        walletDO.setBalanceUsable(balanceUsable);
        walletDO.setBalanceFrozen(balanceFrozen);
        walletDO.setIncomeTotal(incomeTotal);
        walletDO.setExpenditureTotal(expenditureTotal);
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

    /**
     * 生成钱包子记录流水号
     *
     * @return 流水号
     */
    private String walletSubRecordSerialNumber() {
        return subRecordSerialNumberPrefix + idService.generateId();
    }

}
