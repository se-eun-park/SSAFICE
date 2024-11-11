package com.jetty.ssafficebe.common.config;

import co.elastic.clients.transport.TransportUtils;
import javax.net.ssl.SSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

public class ESConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    private String host;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Value("${elasticsearch.fingerprint}")
    private String fingerprint;


    @Override
    public ClientConfiguration clientConfiguration() {
        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerprint);
        return ClientConfiguration.builder()
                                  .connectedTo(host)
                                  .usingSsl(sslContext, (hostname, session) -> true) // ssl 사용
                                  .withBasicAuth(username, password)
                                  .build();

    }
}
