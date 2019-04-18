package com.pay.asset.core.service.impl;

import com.pay.asset.client.constants.SpringBeanNamespaces;
import com.pay.asset.client.constants.WalletRecordPaymentTypeEnum;
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
import com.pay.asset.core.service.IWalletService;
import com.pay.asset.core.utils.SpringContextUtil;
import com.pay.asset.core.wallet.IWalletDetailRecordHandle;
import com.pay.common.core.service.IDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
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

    @Autowired
    public WalletServiceImpl(IDistributedLockService distributedLockService, IUniqueWalletRecordService uniqueWalletRecordService, WalletDao walletDao, WalletDetailAgentDao walletDetailAgentDao, WalletDetailMemberDao walletDetailMemberDao) {
        this.distributedLockService = distributedLockService;
        this.uniqueWalletRecordService = uniqueWalletRecordService;
        this.walletDao = walletDao;
        this.walletDetailAgentDao = walletDetailAgentDao;
        this.walletDetailMemberDao = walletDetailMemberDao;
    }


    /**
     * 订单状态变化才会触发钱包变化
     *
     * @param walletRecordDTO 钱包记录参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void walletRecord(WalletRecordDTO walletRecordDTO) {
        String lockKey = walletRecordDTO.getOrderType().name() + walletRecordDTO.getOrderNumber() + walletRecordDTO.getOrderStatus().name();
        try {
            if (walletRecordDTO.getSubRecords().size() <= 0) {
                log.error("钱包记录失败，子记录数小于0：{}", walletRecordDTO);
            }
            WalletDO walletDO = walletDao.selectByOwner(walletRecordDTO.getOwnNumber(), walletRecordDTO.getOwnRole().name());
            if (walletDO == null) {
                log.error("钱包记录失败，钱包不存在，编号:{},角色：{}", walletRecordDTO.getOwnNumber(), walletRecordDTO.getOwnRole());
            }
            distributedLockService.lock(lockKey);
            //todo 钱包变化。需要去重操作（订单号去重）
            if (!uniqueWalletRecordService.existed(walletRecordDTO.getOrderType(), walletRecordDTO.getOrderNumber(), walletRecordDTO.getOrderStatus())) {
                //1.查询钱包
                //2.根据子记录计算钱包变化、钱包详情变化和生成子记录
                BigDecimal balanceTotal = walletDO.getBalanceTotal();
                BigDecimal balanceFrozen = walletDO.getBalanceFrozen();
                BigDecimal balanceUsable = walletDO.getBalanceUsable();
                BigDecimal expenditureTotal = walletDO.getExpenditureTotal();
                BigDecimal incomeTotal = walletDO.getIncomeTotal();
                IWalletDetailRecordHandle walletDetailRecordHandle = (IWalletDetailRecordHandle) SpringContextUtil.getBean(SpringBeanNamespaces.WALLET_DETAIL_HANDLE + walletRecordDTO.getOwnRole().name());
                AbstractWalletDetailBaseDO walletDetailBaseDO;
                switch (walletRecordDTO.getOwnRole()) {
                    case MEMBER:
                        walletDetailBaseDO = walletDetailMemberDao.selectByWalletId(walletDO.getId());
                        break;
                    case AGENT:
                        walletDetailBaseDO = walletDetailAgentDao.selectByWalletId(walletDO.getId());
                        break;
                    default:
                        throw new RuntimeException("不支持钱包角色类型：" + walletRecordDTO.getOwnRole());
                }


                for (WalletSubRecordDTO subRecord : walletRecordDTO.getSubRecords()) {
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

                    //钱包详情记录
                    if(subRecord.getPaymentType() != WalletRecordPaymentTypeEnum.FROZEN){
                        walletDetailRecordHandle.record(walletDetailBaseDO, subRecord.getTradeType(), subRecord.getAmount());
                    }
                }
                walletDO.setBalanceTotal(balanceTotal);
                walletDO.setBalanceUsable(balanceUsable);
                walletDO.setBalanceFrozen(balanceFrozen);
                walletDO.setIncomeTotal(incomeTotal);
                walletDO.setExpenditureTotal(expenditureTotal);

                log.info("钱包记录后：{}", walletDO);
                log.info("钱包详情记录后：{}", walletDetailBaseDO);

                //3.更新钱包、更新钱包详情
                walletDao.updateByPrimaryKeySelective(walletDO);
                switch (walletRecordDTO.getOwnRole()) {
                    case AGENT:
                        walletDetailAgentDao.updateByPrimaryKey((WalletDetailAgentDO) walletDetailBaseDO);
                        break;
                    case MEMBER:
                        walletDetailMemberDao.updateByPrimaryKeySelective((WalletDetailMemberDO) walletDetailBaseDO);
                        break;
                    default:
                        //跳过
                }

                //4.插入钱包子记录


            } else {
                log.info("钱包记录操作去重：订单类型：{},订单号：{}");
            }
        } finally {
            distributedLockService.unlock(lockKey);
        }
    }
}
