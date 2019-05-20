package com.pay.asset.client.dto.rest;

import com.pay.common.client.dto.rest.BaseResultFeignDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 交易统计日期分组结果DTO
 *
 * @author chenwei
 * @date 2019/5/20 17:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TradeStatsDateGroupResultFeignDTO extends BaseResultFeignDTO {

    private static final long serialVersionUID = 5550459495917230840L;

    /**
     * 数组
     */
    private List<TradeStatsDTO> list;

}
