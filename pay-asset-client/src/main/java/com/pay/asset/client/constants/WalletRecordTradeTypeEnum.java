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
     * 会员提现税费
     */
    MEMBER_WITHDRAW_TAX,

    /**
     * 会员提现服务费
     */
    MEMBER_WITHDRAW_SERVICE_FEE,

    /**
     * 代理商交易服务费分润
     */
    AGENT_TRADE_SERVICE_PROFIT,

    /**
     * 代理商交易通道分润
     */
    AGENT_TRADE_CHANNEL_PROFIT,



    ///////////支出
    /**
     * 会员交易通道成本费
     */
    MEMBER_TRADE_CHANNEL_FEE,

    /**
     * 会员交易服务费
     */
    MEMBER_TRADE_SERVICE_FEE,

    /**
     * 提现税费
     */
    WITHDRAW_TAX,


    ;


}
