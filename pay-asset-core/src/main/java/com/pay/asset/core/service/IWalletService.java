package com.pay.asset.core.service;

import com.pay.asset.client.dto.WalletRecordDTO;

/**
 * 钱包服务接口
 *
 * @author chenwei
 * @date 2019/4/17 14:16
 */
public interface IWalletService {

    /**
     * 钱包记录
     *
     * @param walletRecordDTO 参数dto
     */
    void walletRecord(WalletRecordDTO walletRecordDTO);

}
