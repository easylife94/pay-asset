package com.pay.asset.client.constants;

/**
 * @author chenwei
 * @date 2019/3/26 10:26
 */
public class PayAssetMessageQueueNames {


    /**
     * 消息队列前缀
     */
    private static final String QUEUE_PREFIX = "pay.asset.";

    /**
     * 测试消息队列
     */
    public static final String QUEUE_TEST = QUEUE_PREFIX + "test";

    /**
     * 交易统计消息队列
     */
    public static final String QUEUE_TRADE_STATISTICS = QUEUE_PREFIX + "tradeStatistics";
}
