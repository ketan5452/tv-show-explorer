package com.example.tvshowapp.config;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ketan Deshmukh
 * on 11/05/25
 */
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpClient client = HttpClients.custom()
                .setUserAgent("TvShowApp/1.0 (ketan@example.com)")
                .setMaxConnTotal(20)
                .setMaxConnPerRoute(5)
                .build();
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
    }
}
