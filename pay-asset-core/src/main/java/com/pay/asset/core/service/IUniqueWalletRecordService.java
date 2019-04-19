package com.pay.asset.core.service;

import com.pay.asset.client.dto.WalletRecordDTO;

/**
 * 唯一钱包记录服务
 *
 * @author chenwei
 * @date 2019/4/17 18:00
 */
public interface IUniqueWalletRecordService {

    /**
     * 是否存在唯一key
     *
     * @param walletRecordDTO 钱包记录参数
     * @return 当且仅当存在返回true，反之返回false
     */
    Boolean existed(WalletRecordDTO walletRecordDTO);

    /**
     * 返回唯一key
     *
     * @param walletRecordDTO 钱包记录参数
     * @return 唯一key
     */
    String uniqueKey(WalletRecordDTO walletRecordDTO);

    /**
     * 插入唯一key记录
     *
     * @param walletRecordDTO 钱包记录参数
     * @return 成功返回true反之返回false
     */
    Boolean insert(WalletRecordDTO walletRecordDTO);
}
