package com.pay.asset.web.rest;

import com.pay.asset.client.constants.TradeStatsDateGroupEnum;
import com.pay.asset.client.dto.rest.TradeStatsDateGroupResultFeignDTO;
import com.pay.asset.client.dto.rest.TradeStatsFeignDTO;
import com.pay.asset.client.dto.rest.TradeStatsResultFeignDTO;
import com.pay.asset.client.service.IPayAssetFeignService;
import com.pay.asset.core.service.IElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenwei
 * @date 2019/5/20 17:42
 */
@Slf4j
@RestController
public class PayAssetFeignServiceImpl implements IPayAssetFeignService {

    private final IElasticsearchService elasticsearchService;

    @Autowired
    public PayAssetFeignServiceImpl(IElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public String test() {
        log.info("test service is available");
        return "service is available";
    }

    @Override
    public TradeStatsResultFeignDTO tradeStats(TradeStatsFeignDTO tradeStatsFeignDTO) {
        //todo 交易数据统计
        return null;
    }

    @Override
    public TradeStatsDateGroupResultFeignDTO tradeStatsDateGroup(TradeStatsFeignDTO tradeStatsFeignDTO, TradeStatsDateGroupEnum tradeStatsDateGroup) {
        //todo 交易数据按日期分组统计
        return null;
    }
}
