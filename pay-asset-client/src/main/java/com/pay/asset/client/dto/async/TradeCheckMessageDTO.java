package com.pay.asset.client.dto.async;

import com.pay.common.client.constants.CheckDayEnum;
import com.pay.common.client.constants.CheckMethodEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易结算消息DTO
 *
 * @author chenwei
 * @date 2019-04-23
 */
@Data
public class TradeCheckMessageDTO implements Serializable {

    private String sysOrderNumber;

    private BigDecimal tradeAmount;

    private CheckDayEnum checkDay;

    private CheckMethodEnum checkMethod;

    private Integer checkTimeHour;

    private Integer checkTimeMin;
}
