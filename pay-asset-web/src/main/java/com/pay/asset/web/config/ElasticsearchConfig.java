package com.pay.asset.web.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author chenwei
 * @date 2019/5/14 10:27
 */
@Slf4j
@Configuration
public class ElasticsearchConfig {
    private static final int ADDRESS_LENGTH = 2;

    @Value("${elasticsearch.ip}")
    private String[] ipAddress;

    @Value("${elasticsearch.max-retry-timeout}")
    private Integer maxRetryTimeoutMillis;

    @Value("${elasticsearch.http-scheme}")
    private String httpScheme;

    @Bean
    public RestClientBuilder restClientBuilder() {
        HttpHost[] hosts = Arrays.stream(ipAddress)
                .map(this::makeHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        log.debug("elasticsearch hosts:{}", Arrays.toString(hosts));
        return RestClient.builder(hosts);
    }

    @Bean
    public RestHighLevelClient esRestClient(RestClientBuilder restClientBuilder) {
        restClientBuilder.setMaxRetryTimeoutMillis(maxRetryTimeoutMillis);
        return new RestHighLevelClient(restClientBuilder);
    }


    private HttpHost makeHttpHost(String s) {
        assert StringUtils.isNotEmpty(s);
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, httpScheme);
        } else {
            return null;
        }
    }

}
