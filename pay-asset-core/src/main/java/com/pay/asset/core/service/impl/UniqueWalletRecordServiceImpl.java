package com.pay.asset.core.service.impl;

import com.pay.asset.client.dto.WalletRecordDTO;
import com.pay.asset.client.model.UniqueWalletRecordDO;
import com.pay.asset.core.dao.UniqueWalletRecordDao;
import com.pay.asset.core.service.IUniqueWalletRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei
 * @date 2019/4/17 18:03
 */
@Slf4j
@Service
public class UniqueWalletRecordServiceImpl implements IUniqueWalletRecordService {

    private static final String SPLIT = ":";

    private final UniqueWalletRecordDao uniqueWalletRecordDao;

    @Autowired
    public UniqueWalletRecordServiceImpl(UniqueWalletRecordDao uniqueWalletRecordDao) {
        this.uniqueWalletRecordDao = uniqueWalletRecordDao;
    }


    @Override
    public Boolean existed(WalletRecordDTO walletRecordDTO) {
        String uniqueKey = uniqueKey(walletRecordDTO);
        return uniqueWalletRecordDao.count(uniqueKey) > 0;
    }

    /**
     * key = 订单类型:订单号:订单状态:拥有者类型:拥有者编号
     *
     * @param walletRecordDTO 钱包记录参数
     * @return 唯一key
     */
    @Override
    public String uniqueKey(WalletRecordDTO walletRecordDTO) {
        return new StringBuilder()
                .append(walletRecordDTO.getOrderType().name())
                .append(SPLIT)
                .append(walletRecordDTO.getOrderNumber())
                .append(SPLIT)
                .append(walletRecordDTO.getOrderStatus().name())
                .append(SPLIT)
                .append(walletRecordDTO.getOwnRole().name())
                .append(SPLIT)
                .append(walletRecordDTO.getOwnNumber())
                .toString();
    }

    @Override
    public Boolean insert(WalletRecordDTO walletRecordDTO) {
        String uniqueKey = uniqueKey(walletRecordDTO);
        UniqueWalletRecordDO uniqueWalletRecordDO = new UniqueWalletRecordDO(uniqueKey);
        return uniqueWalletRecordDao.insert(uniqueWalletRecordDO) > 0;
    }
}
