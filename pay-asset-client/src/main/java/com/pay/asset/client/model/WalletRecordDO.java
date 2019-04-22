package com.pay.asset.client.model;

import com.pay.common.client.model.AbstractBaseDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenwei
 * @date 2019/4/22 10:40
 */
@Data
public class WalletRecordDO extends AbstractBaseDO {

    private Long walletId;

    private String orderType;

    private String orderNumber;

    private String orderStatus;

    private String paymentType;

    private String tradeType;

    private Date tradeTime;

    private BigDecimal tradeAmount;

    private String serialNumber;

    private BigDecimal balanceTotalBefore;

    private BigDecimal balanceTotalAfter;

    private BigDecimal balanceUsableBefore;

    private BigDecimal balanceUsableAfter;

    private BigDecimal balanceFrozenBefore;

    private BigDecimal balanceFrozenAfter;

    public WalletRecordDO(Long id) {
        super(id);
    }

    public WalletRecordDO() {
    }
}
