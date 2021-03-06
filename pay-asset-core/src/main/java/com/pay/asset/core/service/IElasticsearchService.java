package com.pay.asset.core.service;

import com.pay.asset.client.document.BaseDocument;

/**
 * @author chenwei
 * @date 2019/5/14 10:39
 */
public interface IElasticsearchService {

    /**
     * 索引文档
     *
     * @param document
     * @return
     */
    boolean index(BaseDocument document);



}
