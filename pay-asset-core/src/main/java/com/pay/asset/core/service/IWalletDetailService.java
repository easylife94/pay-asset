package com.pay.asset.core.service;

import com.pay.asset.client.constants.WalletOwnRoleEnum;
import com.pay.asset.client.model.AbstractWalletDetailBaseDO;

/**
 * @author chenwei
 * @date 2019/4/19 11:42
 */
public interface IWalletDetailService {

    /**
     * 查询钱包详情
     *
     * @param ownRole  拥有者角色
     * @param walletId 钱包id
     * @return
     */
    AbstractWalletDetailBaseDO selectByWalletId(WalletOwnRoleEnum ownRole, Long walletId);

    /**
     * 更新钱包详情
     * @param ownRole  拥有者角色
     * @param walletDetailDO 钱包详情
     * @return 更新成功返回true
     */
    Boolean update(WalletOwnRoleEnum ownRole, AbstractWalletDetailBaseDO walletDetailDO);
}
