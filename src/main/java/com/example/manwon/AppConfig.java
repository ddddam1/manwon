package com.example.manwon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class AppConfig {

    @Bean
    public WebClient productPriceWebClient(){
        String BASE_URL = "http://openapi.price.go.kr/openApiImpl/ProductPriceInfoService/getProductPriceInfoSvc.do";
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .baseUrl(BASE_URL)
                .build();
    }

    @Bean
    public WebClient productNameWebClient(){
        String BASE_URL = "http://openapi.price.go.kr/openApiImpl/ProductPriceInfoService/getProductInfoSvc.do";
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .baseUrl(BASE_URL)
                .build();
    }
}
