package com.pay.asset.client.service.hystric;

import com.pay.asset.client.constants.TradeStatsDateGroupEnum;
import com.pay.asset.client.dto.rest.TradeStatsDateGroupResultFeignDTO;
import com.pay.asset.client.dto.rest.TradeStatsFeignDTO;
import com.pay.asset.client.dto.rest.TradeStatsResultFeignDTO;
import com.pay.asset.client.service.client.IPayAssetFeignServiceClient;
import org.springframework.stereotype.Component;

/**
 * @author chenwei
 * @date 2019/5/20 13:44
 */
@Component
public class PayAssetFeignServiceFallback implements IPayAssetFeignServiceClient {

    @Override
    public String test() {
        return "Sorry pay api service is unavailable";
    }

    @Override
    public TradeStatsResultFeignDTO tradeStats(TradeStatsFeignDTO tradeStatsFeignDTO) {
        TradeStatsResultFeignDTO resultFeignDTO = new TradeStatsResultFeignDTO();
        resultFeignDTO.feignFail("SERVICE_UNAVAILABLE", "服务不可用");
        return resultFeignDTO;
    }

    @Override
    public TradeStatsDateGroupResultFeignDTO tradeStatsDateGroup(TradeStatsFeignDTO tradeStatsFeignDTO, TradeStatsDateGroupEnum tradeStatsDateGroup) {
        TradeStatsDateGroupResultFeignDTO resultFeignDTO = new TradeStatsDateGroupResultFeignDTO();
        resultFeignDTO.feignFail("SERVICE_UNAVAILABLE", "服务不可用");
        return resultFeignDTO;
    }
}
