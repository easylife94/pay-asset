package com.pay.asset.client.constants;

/**
 * @author chenwei
 * @date 2019/5/14 17:24
 */
public enum ElasticsearchIndexEnum {

    /**
     * 交易日志索引
     */
    TRADE_LOG("trade_log"),
    ;

    private String index;

    ElasticsearchIndexEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
