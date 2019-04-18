package com.pay.asset.client.constants;

/**
 * 钱包记录交易类型
 *
 * @author chenwei
 * @date 2019/4/16 17:24
 */
public enum WalletRecordTradeTypeEnum {


    ///////////收入
    /**
     * 会员充值
     */
    MEMBER_RECHARGE,

    /**
     * 代理商交易服务费分润
     */
    TRADE_SERVICE_PROFIT,

    /**
     * 交易通道分润
     */
    TRADE_CHANNEL_PROFIT,


    ///////////支出
    /**
     * 交易通道成本费
     */
    TRADE_CHANNEL_FEE,

    /**
     * 交易服务费
     */
    TRADE_SERVICE_FEE,

    /**
     * 提现解冻（实际支出）
     */
    WITHDRAW_UNFROZEN,

    /**
     * 提现冻结（未实际支出）
     */
    WITHDRAW_FROZEN,

    /**
     * 提现税费
     */
    WITHDRAW_TAX,

    /**
     * 提现服务费
     */
    WITHDRAW_SERVICE_FEE,


    ;


}
