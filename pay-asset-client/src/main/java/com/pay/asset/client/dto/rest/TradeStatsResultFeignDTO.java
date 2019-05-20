package com.pay.asset.client.dto.rest;

import com.pay.common.client.dto.rest.BaseResultFeignDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易统计结果DTO
 *
 * @author chenwei
 * @date 2019/5/20 17:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TradeStatsResultFeignDTO extends BaseResultFeignDTO {

    private static final long serialVersionUID = 5365403051231932878L;

    /**
     * 数据
     */
    private TradeStatsDTO data;

}
