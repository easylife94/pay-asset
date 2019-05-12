package com.pay.asset.client.model;

import com.pay.common.client.model.AbstractBaseDO;
import lombok.Data;

import java.util.Date;

/**
 * @author chenwei
 * @date 2019-05-12
 */
@Data
public class FestivalDayDO extends AbstractBaseDO {

    /**
     * 节日日期
     */
    private Date festivalDate;

    /**
     * 节日名称
     */
    private String festivalName;


}
