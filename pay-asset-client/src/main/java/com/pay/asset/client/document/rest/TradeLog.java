package com.pay.asset.client.document.rest;

import lombok.Data;

/**
 * 交易日志
 *
 * @author chenwei
 * @date 2019/5/14 10:47
 */
@Data
public class TradeLog {

    private String sysOrderNumber;

    private Long tradeAmount;

    private Long tradeServiceFee;

    private Long tradeTimestamp;

    private String platformNumber;

    private String channelNumber;

    private String agentNumber;

    private String memberNumber;

    private String merchantNumber;

    public TradeLog(String sysOrderNumber, Long tradeAmount, Long tradeServiceFee, Long tradeTimestamp,
                    String platformNumber, String channelNumber, String agentNumber, String memberNumber, String merchantNumber) {
        this.sysOrderNumber = sysOrderNumber;
        this.tradeAmount = tradeAmount;
        this.tradeServiceFee = tradeServiceFee;
        this.tradeTimestamp = tradeTimestamp;
        this.platformNumber = platformNumber;
        this.channelNumber = channelNumber;
        this.agentNumber = agentNumber;
        this.memberNumber = memberNumber;
        this.merchantNumber = merchantNumber;
    }
}
