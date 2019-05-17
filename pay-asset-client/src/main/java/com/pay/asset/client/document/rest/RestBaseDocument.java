package com.pay.asset.client.document.rest;

import com.pay.asset.client.document.BaseDocument;
import lombok.Data;

/**
 * @author chenwei
 * @date 2019/5/14 13:35
 */
@Data
public class RestBaseDocument<T> extends BaseDocument {

    private T source;

    public RestBaseDocument(String index, T source) {
        super(index);
        this.source = source;
    }
}
