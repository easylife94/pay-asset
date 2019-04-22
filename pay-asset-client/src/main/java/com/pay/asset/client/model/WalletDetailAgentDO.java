package com.pay.asset.client.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 代理商钱包详情
 *
 * @author chenwei
 * @date 2019/4/18 14:29
 */
@Data
public class WalletDetailAgentDO extends AbstractWalletDetailBaseDO {

    /**
     * 收入 - 分润
     */
    private BigDecimal incomeProfit;

    /**
     * 支出 - 提现
     */
    private BigDecimal expenditureWithdraw;

    /**
     * 支出 - 提现费用
     */
    private BigDecimal expenditureWithdrawFee;

    public WalletDetailAgentDO(Long id) {
        super(id);
    }

    public WalletDetailAgentDO() {
    }
}
