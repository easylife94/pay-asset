package com.pay.asset.web.core.service;

import com.pay.asset.client.document.rest.RestBaseDocument;
import com.pay.asset.client.document.rest.TradeLog;
import com.pay.asset.core.service.IElasticsearchService;
import com.pay.asset.web.PayAssetWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chenwei
 * @date 2019/5/14 11:02
 */
public class ElasticsearchServiceTests extends PayAssetWebApplicationTests {

    @Autowired
    private IElasticsearchService elasticsearchService;

    @Test
    public void testIndex() {
        String sysOrderNumber = "TEST-" + System.currentTimeMillis();
        TradeLog tradeLog = new TradeLog(sysOrderNumber, 1000L,
                10L, System.currentTimeMillis(), "WAIT", "TEST-00001",
                "TEST-00002", "TEST-00003", "TEST-00004", "TEST-00005",
                "ALI","ALI_NATIVE","2019-05-16");
        RestBaseDocument<TradeLog> document = new RestBaseDocument<>("trade-log", tradeLog);
        document.setId(sysOrderNumber);
        System.out.println(tradeLog);
        elasticsearchService.index(document);
    }

}
