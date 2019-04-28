package com.pay.asset.web.config;

import com.pay.asset.core.service.ICheckTradeService;
import com.pay.common.core.service.IDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 静态定时任务配置
 *
 * @author chenwei
 * @date 2019/4/24 10:20
 */
@Slf4j
@Configuration
public class ScheduleConfig {
    @Value("${check.trade.task-handle-count}")
    private Long checkTradeTaskHandleCount;

    private final ICheckTradeService checkTradeService;

    @Autowired
    public ScheduleConfig(ICheckTradeService checkTradeService, IDistributedLockService distributedLockService) {
        this.checkTradeService = checkTradeService;
    }

    /**
     * 开始结算交易
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void beginCheckTrade() {
        log.info("结算交易定时任务开始，异步处理结算个数：{}", checkTradeTaskHandleCount);
        checkTradeService.asyncRunCheckTradeTask(checkTradeTaskHandleCount);
        log.info("结算交易定时任务结束，异步处理结算个数：{}", checkTradeTaskHandleCount);
    }

}
