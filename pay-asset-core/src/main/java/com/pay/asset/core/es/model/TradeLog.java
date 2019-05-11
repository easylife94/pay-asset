package com.pay.asset.core.es.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * 交易日志
 *
 * @author chenwei
 * @date 2019/5/11 10:51
 */
@Document(indexName = "trade", type = "log")
@Data
public class TradeLog {

    @Id
    private Long id;

    /**
     * 交易时间戳
     */
    @Field
    private Long tradeTimeStamp;

    /**
     * 交易金额，单位：分
     */
    @Field
    private Long tradeAmount;

    /**
     * 交易服务费，单位：分
     */
    @Field
    private Long tradeServiceFee;

    /**
     * 交易状态
     */
    @Field
    private String tradeStatus;

    /**
     * 平台id
     */
    @Field
    private Long platformId;

    /**
     * 通道id
     */
    @Field
    private Long channelId;

    /**
     * 商户id
     */
    @Field
    private Long merchantId;

    /**
     * 代理商id
     */
    @Field
    private Long agentId;

    /**
     * 会员id
     */
    @Field
    private Long memberId;

}

