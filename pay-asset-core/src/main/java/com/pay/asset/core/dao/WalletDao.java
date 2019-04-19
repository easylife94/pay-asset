package com.pay.asset.core.dao;

import com.pay.asset.client.model.WalletDO;
import com.pay.common.core.dao.IBaseDao;

/**
 * @author chenwei
 * @date 2019/4/18 10:07
 */
public interface WalletDao extends IBaseDao<WalletDO> {

    /**
     * 根据钱包拥有者查询
     *
     * @param ownNumber 拥有者编号
     * @param ownRole   拥有者角色
     * @return 钱包记录
     */
    WalletDO selectByOwner(String ownNumber, String ownRole);
}
