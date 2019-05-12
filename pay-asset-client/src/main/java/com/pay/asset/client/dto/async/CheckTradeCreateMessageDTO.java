package com.pay.asset.client.dto.async;

import com.pay.asset.client.dto.CheckTradeCreateDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * 结算交易创建消息DTO
 *
 * @author chenwei
 * @date 2019/4/24 17:41
 */
@Data
public class CheckTradeCreateMessageDTO extends CheckTradeCreateDTO implements Serializable {
    private static final long serialVersionUID = -5064117341127244182L;


}
