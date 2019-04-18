package com.pay.asset.core.service.impl;

import com.pay.asset.client.constants.WalletRecordOrderStatusEnum;
import com.pay.asset.client.constants.WalletRecordOrderTypeEnum;
import com.pay.asset.core.service.IUniqueWalletRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chenwei
 * @date 2019/4/17 18:03
 */
@Slf4j
@Service
public class UniqueWalletRecordServiceImpl implements IUniqueWalletRecordService {

    @Override
    public Boolean existed(WalletRecordOrderTypeEnum orderType, String orderNumber, WalletRecordOrderStatusEnum orderStatus) {
        return false;
    }
}
