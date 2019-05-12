package com.pay.asset.client.dto;

import com.pay.common.client.constants.CheckDayEnum;
import com.pay.common.client.constants.CheckMethodEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenwei
 * @date 2019-05-04
 */
@Data
public class CheckTradeCreateDTO {

    /**
     * 系统订单号
     */
    private String sysOrderNumber;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 交易日期
     */
    private Date tradeDate;

    /**
     * 结算日枚举
     */
    private CheckDayEnum checkDay;

    /**
     * 结算方式
     */
    private CheckMethodEnum checkMethod;

    /**
     * 结算时间小时
     */
    private Integer checkTimeHour;

    /**
     * 结算时间分钟
     */
    private Integer checkTimeMin;

}
