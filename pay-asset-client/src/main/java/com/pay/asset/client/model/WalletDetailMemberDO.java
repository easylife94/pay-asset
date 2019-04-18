package com.pay.asset.client.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员钱包详情
 *
 * @author chenwei
 * @date 2019/4/18 14:29
 */
@Data
public class WalletDetailMemberDO extends AbstractWalletDetailBaseDO {

    /**
     * 收入 - 充值
     */
    private BigDecimal incomeRecharge;

    /**
     * 支出 - 提现
     */
    private BigDecimal expenditureWithdraw;

    /**
     * 支出 - 交易费用
     */
    private BigDecimal expenditureTradeFee;

    /**
     * 支出 - 提现费用
     */
    private BigDecimal expenditureWithdrawFee;

}
