package com.pay.asset.web.core.service;

import com.pay.asset.client.dto.CheckTradeCreateDTO;
import com.pay.asset.core.service.ICheckTradeService;
import com.pay.asset.web.PayAssetWebApplicationTests;
import com.pay.common.client.constants.CheckDayEnum;
import com.pay.common.client.constants.CheckMethodEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenwei
 * @date 2019-05-12
 */
public class CheckTradeServiceTests extends PayAssetWebApplicationTests {

    @Autowired
    private ICheckTradeService checkTradeService;

    @Test
    public void testAsyncRunCheckTradeTask() {

        //todo 测试异步处理交易结算任务

        checkTradeService.asyncRunCheckTradeTask(10L);
    }

    @Test
    public void testCreateCheckTrade() {
        CheckTradeCreateDTO checkTradeCreateDTO = new CheckTradeCreateDTO();
        checkTradeCreateDTO.setCheckDay(CheckDayEnum.T1);
        checkTradeCreateDTO.setCheckMethod(CheckMethodEnum.AUTO);
        checkTradeCreateDTO.setSysOrderNumber(System.currentTimeMillis() + "");
        checkTradeCreateDTO.setTradeAmount(new BigDecimal("10.00"));
        checkTradeCreateDTO.setTradeDate(new Date());
        checkTradeCreateDTO.setCheckTimeHour(23);
        checkTradeCreateDTO.setCheckTimeMin(0);
        checkTradeService.createCheckTrade(checkTradeCreateDTO);
    }
}
