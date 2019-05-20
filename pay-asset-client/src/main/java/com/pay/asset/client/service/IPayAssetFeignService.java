package com.pay.asset.client.service;

import com.pay.asset.client.constants.TradeStatsDateGroupEnum;
import com.pay.asset.client.dto.rest.TradeStatsDTO;
import com.pay.asset.client.dto.rest.TradeStatsDateGroupResultFeignDTO;
import com.pay.asset.client.dto.rest.TradeStatsFeignDTO;
import com.pay.asset.client.dto.rest.TradeStatsResultFeignDTO;

/**
 * @author chenwei
 * @date 2019/5/20 10:27
 */
public interface IPayAssetFeignService {

    /**
     * 测试服务是否可用
     *
     * @return
     */
    String test();

    /**
     * 交易统计
     *
     * @param tradeStatsFeignDTO 参数
     * @return 交易统计数据
     */
    TradeStatsResultFeignDTO tradeStats(TradeStatsFeignDTO tradeStatsFeignDTO);

    /**
     * 交易按日期分组统计
     *
     * @param tradeStatsFeignDTO  查询参数
     * @param tradeStatsDateGroup 分组枚举
     * @return
     */
    TradeStatsDateGroupResultFeignDTO tradeStatsDateGroup(TradeStatsFeignDTO tradeStatsFeignDTO, TradeStatsDateGroupEnum tradeStatsDateGroup);

}
