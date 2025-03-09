package io.backendscience.javabenchmark.platformthread;

import io.backendscience.javabenchmark.model.Todo;
import java.util.concurrent.ExecutionException;
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
public class VirtualThreadController {

    private final RestClient restClient;
    private final Logger logger = Logger.getLogger(VirtualThreadController.class.getName());
//    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @GetMapping("virtual-thread")
    public ResponseEntity<Todo> getVirtualThread() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("Start Controller isVirtualThread: " + Thread.currentThread().isVirtual());

//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                ResponseEntity<String> result = restClient.get()
//                    .uri("/todos/1")
//                    .retrieve()
//                    .toEntity(String.class);
//            }
//        }).get();

        ResponseEntity<Todo> result = restClient.get()
            .uri("/todos/1")
            .retrieve()
            .toEntity(Todo.class);

        stopWatch.stop();



        logger.info(String.format("Finish Controller %s ms", stopWatch.getTotalTimeMillis()));

        return ResponseEntity.ok(result.getBody());
    }

}
