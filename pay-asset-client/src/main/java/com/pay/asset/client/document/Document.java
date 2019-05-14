package com.pay.asset.client.document;

import lombok.Data;

/**
 * @author chenwei
 * @date 2019/5/14 10:51
 */
@Data
public abstract class Document {

    /**
     * 必须小写
     */
    private String index;

    public Document(String index) {
        this.index = index;
    }
}
