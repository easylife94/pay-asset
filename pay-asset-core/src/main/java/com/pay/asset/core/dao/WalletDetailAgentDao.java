package com.pay.asset.core.dao;

import com.pay.asset.client.model.WalletDetailAgentDO;

/**
 * @author chenwei
 * @date 2019/4/18 14:29
 */
public interface WalletDetailAgentDao extends IWalletDetailDao<WalletDetailAgentDO> {

    /**
     * {@inheritDoc}
     * @param walletId 钱包id
     * @return
     */
    @Override
    WalletDetailAgentDO selectByWalletId(Long walletId);
}
