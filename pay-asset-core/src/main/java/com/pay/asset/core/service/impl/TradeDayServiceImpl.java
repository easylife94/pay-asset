package com.pay.asset.core.service.impl;

import com.pay.asset.core.dao.FestivalDayDao;
import com.pay.asset.core.service.ITradeDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 交易日服务接口
 * 交易日是指除节假日外周一到周五
 *
 * @author chenwei
 * @date 2019-05-04
 */
@Service
public class TradeDayServiceImpl implements ITradeDayService {

    private final FestivalDayDao festivalDayDao;

    @Autowired
    public TradeDayServiceImpl(FestivalDayDao festivalDayDao) {
        this.festivalDayDao = festivalDayDao;
    }

    @Override
    public Boolean isTradeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            //先判断是否是周末
            return false;
        }
        return festivalDayDao.countByDate(date) <= 0;
    }

    @Override
    public Date nextTradeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (true) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (isTradeDay(calendar.getTime())) {
                return calendar.getTime();
            }
        }
    }
}
