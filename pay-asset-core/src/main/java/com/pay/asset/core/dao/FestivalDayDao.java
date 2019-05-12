package com.pay.asset.core.dao;

import com.pay.asset.client.model.FestivalDayDO;
import com.pay.common.core.dao.IBaseDao;

import java.util.Date;
import java.util.List;

/**
 * 节假日
 *
 * @author chenwei
 * @date 2019-05-12
 */
public interface FestivalDayDao extends IBaseDao<FestivalDayDO> {

    /**
     * 根据日期查询节假日
     *
     * @param date 当前日期
     * @return
     */
    int countByDate(Date date);

    /**
     * 根据开始日期查询节假日
     *
     * @param date
     * @return
     */
    List<FestivalDayDO> selectByBeginDate(Date date);
}
