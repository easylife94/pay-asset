package com.pay.asset.core.dao;

import com.pay.asset.client.model.WalletDetailMemberDO;

/**
 * @author chenwei
 * @date 2019/4/18 14:29
 */
public interface WalletDetailMemberDao extends IWalletDetailDao<WalletDetailMemberDO> {

    @Override
    WalletDetailMemberDO selectByWalletId(Long walletId);
}
