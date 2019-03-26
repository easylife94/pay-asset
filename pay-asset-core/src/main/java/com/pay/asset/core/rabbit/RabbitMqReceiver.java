package com.pay.asset.core.rabbit;

import com.pay.asset.client.constants.PayAssetMessageQueueNames;
import com.pay.asset.client.dto.TradeStatisticsMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息队列接收器
 *
 * @author chenwei
 * @date 2019/1/15 16:34
 */
@Component
@Slf4j
public class RabbitMqReceiver {


    @RabbitListener(queues = PayAssetMessageQueueNames.QUEUE_TRADE_STATISTICS)
    public void tradeCreate(TradeStatisticsMessageDTO tradeStatisticsMessageDTO) {
        try {
            log.info("收到交易统计消息：{}", tradeStatisticsMessageDTO);
            //1.数据统计
        } catch (Exception e) {
            e.printStackTrace();
            log.error("交易创建消息处理异常，消息内容:{}，异常：{}", tradeStatisticsMessageDTO, e.getMessage());
        }
    }


}
