package com.pay.asset.core.service.impl;

import com.pay.asset.client.dto.WalletRecordDTO;
import com.pay.asset.core.service.IUniqueWalletRecordService;
import com.pay.asset.core.service.IWalletService;
import com.pay.common.core.service.IDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwei
 * @date 2019/4/17 14:17
 */
@Slf4j
@Service
public class WalletServiceImpl implements IWalletService {

    private final IDistributedLockService distributedLockService;
    private final IUniqueWalletRecordService uniqueWalletRecordService;

    @Autowired
    public WalletServiceImpl(IDistributedLockService distributedLockService, IUniqueWalletRecordService uniqueWalletRecordService) {
        this.distributedLockService = distributedLockService;
        this.uniqueWalletRecordService = uniqueWalletRecordService;
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
            distributedLockService.lock(lockKey);
            if (!uniqueWalletRecordService.existed(walletRecordDTO.getOrderType(), walletRecordDTO.getOrderNumber(), walletRecordDTO.getOrderStatus())) {
                //todo 钱包变化。需要去重操作（订单号去重）
                if (walletRecordDTO.getSubRecords().size() > 0) {
                    //1.查询钱包
                    //2.根据子记录计算钱包变化、钱包详情变化
                    //3.更新钱包、更新钱包详情
                    //4.生成钱包子记录
                    //5.插入钱包子记录

                } else {
                    log.error("钱包记录失败，子记录数小于0：{}", walletRecordDTO);
                }
            } else {
                log.info("钱包记录操作去重：订单类型：{},订单号：{}");
            }
        } finally {
            distributedLockService.unlock(lockKey);
        }
    }
}
