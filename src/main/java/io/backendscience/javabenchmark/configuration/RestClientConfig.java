package io.backendscience.javabenchmark.configuration;

import io.backendscience.javabenchmark.platformthread.PlatformThreadController;
import java.time.Duration;
import java.util.logging.Logger;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${base.path.url}")
    private String basePathUrl;

    @Value("${restclient.connection-timeout:5000}")
    private int connectionTimeout;

    @Value("${restclient.read-timeout:10000}")
    private int readTimeout;

    @Value("${restclient.socket-timeout:10000}")
    private int socketTimeout;

    @Value("${restclient.max-total-connections:100}")
    private int maxTotalConnections;

    @Value("${restclient.max-per-route-connections:10}")
    private int maxPerRouteConnections;

    private final Logger logger = Logger.getLogger(RestClientConfig.class.getName());

    @Bean
    public RestClient restClient() {
        logger.info("VERSÃO COM TIMEOUT E COM CONNECTION POOLING");

        return RestClient.builder()
            .baseUrl(basePathUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .requestFactory(this.clientHttpRequestFactory())
            .build();
    }

    private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
        poolingConnManager.setMaxTotal(maxTotalConnections);
        poolingConnManager.setDefaultMaxPerRoute(maxPerRouteConnections);

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(Timeout.ofMilliseconds(connectionTimeout))
            .setResponseTimeout(Timeout.ofMilliseconds(socketTimeout))
            .build();

        CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .setDefaultRequestConfig(requestConfig)
            .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(Duration.ofMillis(connectionTimeout));
        factory.setReadTimeout(Duration.ofMillis(readTimeout));

        return factory;
    }


}
