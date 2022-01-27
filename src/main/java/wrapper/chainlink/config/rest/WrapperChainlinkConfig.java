package wrapper.chainlink.config.rest;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import wrapper.chainlink.config.rest.interceptor.JsonContentTypeRestTemplateInterceptor;
import wrapper.chainlink.exeption.integration.CryptopaymentErrorHandler;
import wrapper.chainlink.integration.cryptopayment.properties.CryptoPaymentProperties;
import wrapper.chainlink.service.properties.ChainlinkApiProperties;

@Configuration	
public class WrapperChainlinkConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean(name = "cryptoPaymentRestTemplate")
    public RestTemplate cryptoPaymentRestTemplate(RestTemplateBuilder restTemplateBuilder, CryptoPaymentProperties cryptoPaymentProperties) {
        restTemplateBuilder = restTemplateBuilder.errorHandler(new CryptopaymentErrorHandler())
                .rootUri(cryptoPaymentProperties.getUri())
                .interceptors(new JsonContentTypeRestTemplateInterceptor())
                .setConnectTimeout(Duration.ofMillis(cryptoPaymentProperties.getTimeout().getConnect()))
                .setReadTimeout(Duration.ofMillis(cryptoPaymentProperties.getTimeout().getRead()));
        return restTemplateBuilder.build();
    }

    @Bean(name = "chainlinkApiRestTemplate")
    public RestTemplate chainlinkApiRestTemplate(RestTemplateBuilder restTemplateBuilder, ChainlinkApiProperties chainlinkProperties) {
        restTemplateBuilder = restTemplateBuilder
                .interceptors(new JsonContentTypeRestTemplateInterceptor())
                .setConnectTimeout(Duration.ofMillis(chainlinkProperties.getTimeout().getConnect()))
        		.setReadTimeout(Duration.ofMillis(chainlinkProperties.getTimeout().getRead()));
        return restTemplateBuilder.build();
    }

    private MappingJackson2HttpMessageConverter supportsMediaTypes() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        return converter;
    }
}
