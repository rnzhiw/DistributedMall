package com.aiguigu.gulimall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GulimallElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        COMMON_OPTIONS = builder.build();
    }



    @Bean
    public RestHighLevelClient esRestClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.56.10", 9200, "http"));
        return new RestHighLevelClient(builder);
    }
}
