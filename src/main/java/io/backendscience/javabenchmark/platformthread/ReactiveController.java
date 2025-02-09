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
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("java-benchmark")
@RequiredArgsConstructor
public class ReactiveController {

    private final WebClient webClient;
    private final Logger logger = Logger.getLogger(ReactiveController.class.getName());

    @GetMapping("reactive")
    public Mono<Todo> getReactive() throws InterruptedException {
        Mono<Todo> todoMono = webClient
            .get()
            .uri("/todos/1")
            .retrieve()
            .bodyToMono(Todo.class)
            .onErrorResume(e -> {
                // Handle Todo not found (404) or other errors
                return Mono.error(new RuntimeException("Todo not found or unavailable"));
            });

        return Mono.justOrEmpty(null);
//        return todoMono;
    }

}
