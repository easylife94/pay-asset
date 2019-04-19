package com.pay.asset.client.model;

import com.pay.common.client.model.AbstractUniqueBaseDO;
import lombok.Data;

/**
 * 钱包记录去重表
 *
 * @author chenwei
 * @date 2019/4/19 09:56
 */
@Data
public class UniqueWalletRecordDO extends AbstractUniqueBaseDO {

    public UniqueWalletRecordDO(String uniqueKey) {
        super(uniqueKey);
    }
}
