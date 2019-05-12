package com.pay.asset.client.model;

import com.pay.common.client.model.AbstractBaseDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenwei
 * @date 2019-05-04
 */
@Data
public class CheckTradeDO extends AbstractBaseDO {

    private String sysOrderNumber;

    private BigDecimal tradeAmount;

    private Date checkDate;

    private String checkStatus;

    private String checkMethod;

    public CheckTradeDO(Long id) {
        super(id);
    }

    public CheckTradeDO() {
    }
}
