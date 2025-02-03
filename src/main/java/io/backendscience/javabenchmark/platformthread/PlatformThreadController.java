package io.backendscience.javabenchmark.platformthread;

import io.backendscience.javabenchmark.model.Todo;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("java-benchmark")
@RequiredArgsConstructor
public class PlatformThreadController {

    private final RestClient restClient;
    private final Logger logger = Logger.getLogger(PlatformThreadController.class.getName());
//    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @GetMapping("platform-thread")
    public ResponseEntity<Todo> getPlatformThread() throws ExecutionException, InterruptedException {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                ResponseEntity<String> result = restClient.get()
//                    .uri("/todos/1")
//                    .retrieve()
//                    .toEntity(String.class);
//            }
//        }).get();

//        ResponseEntity<String> result = restClient.get()
//            .uri("delayed")
//            .retrieve()
//            .toEntity(String.class);

        ResponseEntity<Todo> result = restClient.get()
            .uri("/todos/1")
            .retrieve()
            .toEntity(Todo.class);

        stopWatch.stop();

        logger.info(String.format("Finish Platform Threads %s ms", stopWatch.getTotalTimeMillis()));
//
//        System.out.println("Response status: " + result.getStatusCode());
//        System.out.println("Response headers: " + result.getHeaders());
//        System.out.println("Contents: " + result.getBody());

        return ResponseEntity.ok(result.getBody());
//        return "OK";
    }

}
