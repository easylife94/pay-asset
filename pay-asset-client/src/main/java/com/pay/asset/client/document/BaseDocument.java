package com.pay.asset.client.document;

import lombok.Data;

/**
 * @author chenwei
 * @date 2019/5/14 10:51
 */
@Data
public abstract class BaseDocument {

    /**
     * 必须小写
     */
    private String index;

    /**
     * 指定唯一id
     */
    private String id;

    public BaseDocument(String index) {
        this.index = index;
    }
}
