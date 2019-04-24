package com.pay.asset.core.service.impl;

import com.pay.asset.core.service.ICheckTradeService;
import com.pay.common.core.service.IDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwei
 * @date 2019/4/24 11:11
 */
@Slf4j
@Service
public class CheckTradeServiceImpl implements ICheckTradeService {

    private static final String ASYNC_CHECK_TRADE_LOCK = "asyncCheckTradeLock";


    private final IDistributedLockService distributedLockService;

    public CheckTradeServiceImpl(IDistributedLockService distributedLockService) {
        this.distributedLockService = distributedLockService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void asyncRunCheckTradeTask(Long count) {
        try {
            distributedLockService.lock(ASYNC_CHECK_TRADE_LOCK);
            log.info("发送结算交易异步消息");
            //todo 发送结算交易异步消息


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            distributedLockService.unlock(ASYNC_CHECK_TRADE_LOCK);
        }
    }
}
