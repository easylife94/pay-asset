package com.pay.asset.client.document.rest;

import com.pay.asset.client.document.Document;
import lombok.Data;

/**
 * @author chenwei
 * @date 2019/5/14 13:35
 */
@Data
public class RestDocument<T> extends Document {

    private T source;

    public RestDocument(String index, T source) {
        super(index);
        this.source = source;
    }
}
