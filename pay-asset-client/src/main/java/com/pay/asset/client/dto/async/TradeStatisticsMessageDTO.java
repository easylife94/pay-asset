package com.pay.asset.client.dto.async;

import lombok.Data;

import java.io.Serializable;

/**
 * 交易统计消息DTO
 *
 * @author chenwei
 * @date 2019/3/26 10:59
 */
@Data
public class TradeStatisticsMessageDTO implements Serializable {
    private static final long serialVersionUID = 2061439049769829253L;

    private String sysOrderNumber;

    private Long tradeAmount;

    private Long tradeServiceFee;

    private Long tradeTimestamp;

    private String platformNumber;

    private String channelNumber;

    private String memberNumber;

    private String agentNumber;

    private String merchantNumber;

    private String tradeStatus;

    private String defrayalChannel;

    private String defrayalType;

}
