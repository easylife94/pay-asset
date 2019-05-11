package com.pay.asset.core.es;

import com.pay.asset.core.es.model.TradeLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenwei
 * @date 2019/5/11 14:48
 */
@Repository
public interface TradeLogRepository extends ElasticsearchRepository<TradeLog, Long> {
}
