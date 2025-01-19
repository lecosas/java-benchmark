package io.backendscience.javabenchmark.platformthread;

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

    @GetMapping("platform-thread")
    public String getPlatformThread() {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        ResponseEntity<String> result = restClient.get()
            .uri("delayed")
            .retrieve()
            .toEntity(String.class);

        stopWatch.stop();

        logger.info(String.format("Finish Platform Threads %s ms", stopWatch.getTotalTimeMillis()));
//
//        System.out.println("Response status: " + result.getStatusCode());
//        System.out.println("Response headers: " + result.getHeaders());
//        System.out.println("Contents: " + result.getBody());

        return result.getBody();
    }

}
