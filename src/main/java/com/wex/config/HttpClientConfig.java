package com.wex.config;

import com.wex.client.RawTreasuryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Value("${app.uris.treasury}")
    private String treasuryUrl;

    @Bean
    public RawTreasuryClient rawTreasuryClient() {
        RestClientAdapter adapter = RestClientAdapter.create(RestClient.builder().baseUrl(treasuryUrl).build());
        return HttpServiceProxyFactory.builderFor(adapter).build().createClient(RawTreasuryClient.class);

    }
}
