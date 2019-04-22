package com.pay.asset.client.model;

import com.pay.common.client.model.AbstractBaseDO;

/**
 * 抽象钱包详情基类
 *
 * @author chenwei
 * @date 2019/4/18 14:57
 */
public abstract class AbstractWalletDetailBaseDO extends AbstractBaseDO {

    /**
     * 钱包ID
     */
    private Long walletId;

    public AbstractWalletDetailBaseDO(Long id) {
        super(id);
    }

    public AbstractWalletDetailBaseDO() {
    }
}
