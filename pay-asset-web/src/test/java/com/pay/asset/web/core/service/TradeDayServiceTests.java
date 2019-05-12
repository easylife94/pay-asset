package com.pay.asset.web.core.service;

import com.pay.asset.core.service.ITradeDayService;
import com.pay.asset.web.PayAssetWebApplicationTests;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenwei
 * @date 2019-05-12
 */
public class TradeDayServiceTests extends PayAssetWebApplicationTests {

    @Autowired
    private ITradeDayService tradeDayService;

    @Test
    public void testIsTradeDay() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //周日。非交易日
        String dateStr = "2019-09-01";
        Date date = sdf.parse(dateStr);
        Assert.assertTrue(!tradeDayService.isTradeDay(date));

        //周六。非交易日
        dateStr = "2019-03-31";
        date = sdf.parse(dateStr);
        Assert.assertTrue(!tradeDayService.isTradeDay(date));

        //国庆节。非交易日
        dateStr = "2019-10-01";
        date = sdf.parse(dateStr);
        Assert.assertTrue(!tradeDayService.isTradeDay(date));

        //周二。交易日
        dateStr = "2019-10-08";
        date = sdf.parse(dateStr);
        Assert.assertTrue(tradeDayService.isTradeDay(date));
    }

    @Test
    public void testNextTradeDay() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = "2019-09-01";
        String nextTradeDay = "2019-09-02";
        Date date = sdf.parse(dateStr);
        Assert.assertTrue(StringUtils.equals(nextTradeDay, sdf.format(tradeDayService.nextTradeDay(date))));

        dateStr = "2019-08-31";
        nextTradeDay = "2019-09-02";
        date = sdf.parse(dateStr);
        Assert.assertTrue(StringUtils.equals(nextTradeDay, sdf.format(tradeDayService.nextTradeDay(date))));


        dateStr = "2019-10-01";
        nextTradeDay = "2019-10-07";
        date = sdf.parse(dateStr);
        Assert.assertTrue(!StringUtils.equals(nextTradeDay, sdf.format(tradeDayService.nextTradeDay(date))));

        dateStr = "2019-10-01";
        nextTradeDay = "2019-10-08";
        date = sdf.parse(dateStr);
        Assert.assertTrue(StringUtils.equals(nextTradeDay, sdf.format(tradeDayService.nextTradeDay(date))));


    }
}
