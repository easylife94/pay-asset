package com.pay.asset.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pay.asset.client.document.Document;
import com.pay.asset.client.document.rest.RestDocument;
import com.pay.asset.core.service.IElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Elasticsearch REST 客户端实现
 * 7.0.1版本（去掉type和id）
 *
 * @author chenwei
 * @date 2019/5/14 10:43
 */
@Slf4j
@Service
public class ElasticsearchServiceRestImpl implements IElasticsearchService {

    @Autowired
    RestHighLevelClient esRestClient;

    @Override
    public boolean index(Document document) {
        try {
            RestDocument restDocument = (RestDocument) document;
            IndexRequest request = new IndexRequest(document.getIndex());
            request.source(JSONObject.toJSONString(restDocument.getSource()), XContentType.JSON);
            if(document.getId() != null){
                request.id(document.getId());
            }
            IndexResponse response = esRestClient.index(request, RequestOptions.DEFAULT);
            RestStatus status = response.status();
            //todo 判断结果状态
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("索引文档异常，document:{}", document);
            return false;
        }
    }


}
