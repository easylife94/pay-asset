package com.pay.asset.client.dto;

import com.pay.asset.client.constants.WalletRecordPaymentTypeEnum;
import com.pay.asset.client.constants.WalletRecordTradeTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 钱包子记录参数DTO
 *
 * @author chenwei
 * @date 2019/4/17 14:25
 */
@Data
public class WalletSubRecordDTO {

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 收支类型
     */
    private WalletRecordPaymentTypeEnum paymentType;

    /**
     * 交易类型
     */
    private WalletRecordTradeTypeEnum tradeType;
}
