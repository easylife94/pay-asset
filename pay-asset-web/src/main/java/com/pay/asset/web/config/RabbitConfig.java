package com.pay.asset.web.config;


import com.pay.asset.client.constants.PayAssetMessageQueueNames;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 *
 * @author chenwei
 * @date 2018-12-12
 */
@Configuration
public class RabbitConfig {

    /**
     * test 消息队列
     *
     * @return
     */
    @Bean
    public Queue testQueue() {
        return new Queue(PayAssetMessageQueueNames.QUEUE_TEST);
    }

    /**
     * 交易统计队列
     *
     * @return
     */
    @Bean
    public Queue tradeStatisticsQueue() {
        return new Queue(PayAssetMessageQueueNames.QUEUE_TRADE_STATISTICS);
    }
}
