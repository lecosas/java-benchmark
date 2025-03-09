package io.backendscience.javabenchmark.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {

    @Value("${base.path.url}")
    private String basePathUrl;

    @Bean
    public WebClient webClient() {
        ConnectionProvider provider = ConnectionProvider.builder("custom")
            .maxConnections(400)  // ✅ Limit max concurrent connections
            .pendingAcquireTimeout(Duration.ofSeconds(5))  // ✅ Wait up to 5s for a free connection
            .maxIdleTime(Duration.ofSeconds(10))  // ✅ Reuse connections for up to 10s
            .maxLifeTime(Duration.ofMinutes(5))  // ✅ Avoid stale connections
            .evictInBackground(Duration.ofSeconds(30))  // ✅ Clean idle connections
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)  // Connection timeout
            .responseTimeout(Duration.ofSeconds(10))  // Timeout for response
            .doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(10))  // Read timeout
                .addHandlerLast(new WriteTimeoutHandler(10)));  // Write timeout

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl(basePathUrl)
            .build();
    }
}
