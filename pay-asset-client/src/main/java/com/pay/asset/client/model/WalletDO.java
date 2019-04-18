package com.pay.asset.client.model;

import com.pay.common.client.model.AbstractBaseDO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenwei
 * @date 2019/4/18 09:55
 */
@Data
public class WalletDO extends AbstractBaseDO {

    /**
     * 拥有者编号
     */
    private String ownNumber;

    /**
     * 拥有者角色
     */
    private String ownRole;

    /**
     * 总余额
     */
    private BigDecimal balanceTotal;

    /**
     * 可用余额
     */
    private BigDecimal balanceUsable;

    /**
     * 冻结余额
     */
    private BigDecimal balanceFrozen;

    /**
     * 总收入
     */
    private BigDecimal incomeTotal;

    /**
     * 总支出
     */
    private BigDecimal expenditureTotal;

}
