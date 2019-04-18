package com.pay.asset.core.wallet;

import com.pay.asset.client.constants.WalletRecordTradeTypeEnum;
import com.pay.asset.client.model.AbstractWalletDetailBaseDO;

import java.math.BigDecimal;

/**
 * 钱包详情记录处理器
 *
 * @author chenwei
 * @date 2019/4/18 14:50
 */
public interface IWalletDetailRecordHandle {

    /**
     * 计算钱包详情变化，只处理bean
     *
     * @param walletDetailDO  钱包详情
     * @param recordTradeType 钱包记录交易类型
     * @param amount          金额
     */
    void record(AbstractWalletDetailBaseDO walletDetailDO, WalletRecordTradeTypeEnum recordTradeType, BigDecimal amount);
}
