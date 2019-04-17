package com.pay.asset.client.dto;

import com.pay.asset.client.constants.WalletOwnRoleEnum;
import com.pay.asset.client.constants.WalletRecordOrderTypeEnum;
import com.pay.asset.client.constants.WalletRecordPaymentTypeEnum;
import com.pay.asset.client.constants.WalletRecordTradeTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包记录参数DTO
 *
 * @author chenwei
 * @date 2019/4/17 14:19
 */
@Data
public class WalletRecordDTO {

    /**
     * 拥有者编号
     */
    private String ownNumber;

    /**
     * 拥有者角色
     */
    private WalletOwnRoleEnum ownRole;

    /**
     * 订单类型
     */
    private WalletRecordOrderTypeEnum orderType;

    /**
     * 关联订单号
     */
    private String orderNumber;

    /**
     * 子记录
     */
    List<WalletSubRecordDTO> subRecords;
}
