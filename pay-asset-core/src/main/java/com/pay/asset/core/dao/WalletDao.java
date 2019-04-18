package com.pay.asset.core.dao;

import com.pay.asset.client.model.WalletDO;
import com.pay.common.core.dao.IBaseDao;

/**
 * @author chenwei
 * @date 2019/4/18 10:07
 */
public interface WalletDao extends IBaseDao<WalletDO> {

    WalletDO selectByOwner(String ownNumber,String ownRole);
}
