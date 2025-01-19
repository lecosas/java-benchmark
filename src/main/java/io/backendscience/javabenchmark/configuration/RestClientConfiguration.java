package io.backendscience.javabenchmark.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Value("${base.path.url}")
    private String basePathUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            //.requestFactory(new HttpComponentsClientHttpRequestFactory())
            //.messageConverters(converters -> converters.add(new MyCustomMessageConverter()))
            .baseUrl(basePathUrl)
            //.defaultUriVariables(Map.of("variable", "foo"))
            .defaultHeader(HttpHeaders.CONTENT_TYPE.toLowerCase(), MediaType.APPLICATION_JSON.toString())
            //.requestInterceptor(myCustomInterceptor)
            //.requestInitializer(myCustomInitializer)
            .build();

        //TODO: include timeout, respondetimeout, pooling, etc.
    }

}
