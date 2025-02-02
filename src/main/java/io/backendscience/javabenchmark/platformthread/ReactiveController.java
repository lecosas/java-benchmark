package io.backendscience.javabenchmark.platformthread;

import io.backendscience.javabenchmark.model.Todo;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("java-benchmark")
public class ReactiveController {

    private final WebClient webClient;
    private final Logger logger = Logger.getLogger(ReactiveController.class.getName());

    public ReactiveController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    @GetMapping("reactive")
    public Mono<Todo> getReactive() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Mono<Todo> todoMono = webClient
            .get()
            .uri("/todos/1")
            .retrieve()
            .bodyToMono(Todo.class)
            .onErrorResume(e -> {
                // Handle Todo not found (404) or other errors
                return Mono.error(new RuntimeException("Todo not found or unavailable"));
            });

        stopWatch.stop();

        logger.info(String.format("Finish Reactive %s ms", stopWatch.getTotalTimeMillis()));

        return todoMono;
    }

}
