package com.pay.asset.client.dto.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易统计DTO
 *
 * @author chenwei
 * @date 2019/5/20 17:14
 */
@Data
public class TradeStatsDTO{

    private Long tradeCount;

    private Long tradeSucCount;

    private BigDecimal tradeAmountMin;

    private BigDecimal tradeSucAmountMin;

    private BigDecimal tradeAmountMax;

    private BigDecimal tradeSucAmountMax;

    private BigDecimal tradeAmountAvg;

    private BigDecimal tradeSucAmountAvg;

    private BigDecimal tradeAmountSum;

    private BigDecimal tradeSucAmountSum;

    private Date startDate;

    private Date endDate;
}
