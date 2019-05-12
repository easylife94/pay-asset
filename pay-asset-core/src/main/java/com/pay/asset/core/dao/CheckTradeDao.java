package com.pay.asset.core.dao;

import com.pay.asset.client.model.CheckTradeDO;
import com.pay.common.core.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenwei
 * @date 2019-05-04
 */
public interface CheckTradeDao extends IBaseDao<CheckTradeDO> {

    /**
     * 查询待结算订单
     *
     * @param count 数量
     * @return
     */
    List<CheckTradeDO> selectWaitCheck(Long count);

    /**
     * 更新待结算订单为结算中
     *
     * @param checkTradeDOs 记录
     * @return
     */
    int updateChecking(@Param("checkTrades") List<CheckTradeDO> checkTradeDOs);
}

