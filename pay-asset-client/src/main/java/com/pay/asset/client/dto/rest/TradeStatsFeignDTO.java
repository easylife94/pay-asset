package com.pay.asset.client.dto.rest;

import com.pay.common.client.constants.DefrayalChannelEnum;
import com.pay.common.client.constants.DefrayalTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据统计参数DTO
 *
 * @author chenwei
 * @date 2019/5/20 17:30
 */
@Data
public class TradeStatsFeignDTO implements Serializable {
    private static final long serialVersionUID = 3586000203019326138L;

    private Date startDate;

    private Date endDate;

    private String platformNumber;

    private String channelNumber;

    private String memberNumber;

    private String agentNumber;

    private String merchantNumber;

    private DefrayalChannelEnum defrayalChannel;

    private DefrayalTypeEnum defrayalType;

    private BigDecimal minTradeAmount;

    private BigDecimal maxTradeAmount;

    private String orderStatus;

}
