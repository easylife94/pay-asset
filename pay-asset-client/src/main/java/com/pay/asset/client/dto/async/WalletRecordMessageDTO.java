package com.pay.asset.client.dto.async;

import com.pay.asset.client.dto.WalletRecordDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * 钱包记录消息DTO
 *
 * @author chenwei
 * @date 2019/4/17 13:59
 */
@Data
public class WalletRecordMessageDTO extends WalletRecordDTO implements Serializable {

    private static final long serialVersionUID = -1616961801227236220L;
}
