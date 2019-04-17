package com.pay.asset.core.service;

import com.pay.asset.client.constants.WalletRecordOrderStatusEnum;
import com.pay.asset.client.constants.WalletRecordOrderTypeEnum;

/**
 * 唯一钱包记录服务
 *
 * @author chenwei
 * @date 2019/4/17 18:00
 */
public interface IUniqueWalletRecordService {

    Boolean existed(WalletRecordOrderTypeEnum orderType, String orderNumber, WalletRecordOrderStatusEnum orderStatus);

}
