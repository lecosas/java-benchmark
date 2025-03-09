package simulations;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.OpenInjectionStep.RampRate.RampRateOpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.Duration;

public class PlatformThreadSimulation extends Simulation {

    public PlatformThreadSimulation() {
        setUp(buildPostScenario()
            .injectOpen(
                rampUsersPerSec(0).to(50).during(Duration.ofSeconds(30)), //Ramp-up
                rampUsersPerSec(50).to(250).during(Duration.ofSeconds(45)),
                constantUsersPerSec(250).during(Duration.ofSeconds(45)),
                rampUsersPerSec((250)).to(0).during(Duration.ofSeconds(45)))
            .protocols(setupProtocol()))
            .assertions(
                global().responseTime().max().lte(10000),
                global().successfulRequests().percent().gt(90d));
    }

    private static ScenarioBuilder buildPostScenario() {
        return CoreDsl.scenario("Load Test: PlatformThreadSimulation").
            exec(http("java-benchmark").get("/platform-thread")
                .header("Content-Type", "application/json"));
    }

    private static HttpProtocolBuilder setupProtocol() {
        return HttpDsl.http.baseUrl("http://localhost:9999/java-benchmark")
        //return HttpDsl.http.baseUrl("http://192.168.0.246:8080/java-benchmark")
        //return HttpDsl.http.baseUrl("http://54.162.12.56:9999/java-benchmark")
            .acceptHeader("application/json")//.maxConnectionsPerHost(10)
            .userAgentHeader("PlatformThreadSimulation");
    }

    private RampRateOpenInjectionStep injection() {
        int totalUsers = 1000;
        double userRampUpPerInterval = 100;
        double rampUpIntervalInSeconds = 20;

        RampRateOpenInjectionStep step;

        int rampUptimeSeconds = 60;
        int duration = 60;

        return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalInSeconds)).to(totalUsers)
            .during(Duration.ofSeconds(rampUptimeSeconds + duration));
    }
}
