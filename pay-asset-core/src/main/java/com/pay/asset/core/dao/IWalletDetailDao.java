package com.pay.asset.core.dao;

import com.pay.common.core.dao.IBaseDao;

/**
 * 钱包详情接口基类
 *
 * @author chenwei
 * @date 2019/4/18 14:32
 */
public interface IWalletDetailDao<T> extends IBaseDao<T> {

    T selectByWalletId(Long walletId);

}
