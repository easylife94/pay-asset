package com.pay.asset.core.service;

import java.util.Date;

/**
 * @author chenwei
 * @date 2019-05-04
 */
public interface ITradeDayService {

    /**
     * 是否是交易日
     *
     * @param date 日期
     * @return 是交易日则返回true，反之返回false
     */
    Boolean isTradeDay(Date date);

    /**
     * 下个交易日
     *
     * @param date 当前日期
     * @return 下个交易日期
     */
    Date nextTradeDay(Date date);


}
