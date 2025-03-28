package com.eci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class ProxyServiceApplication {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final String[] backendUrls = {
            System.getenv().getOrDefault("BACKEND1", "http://ec2-52-91-70-75.compute-1.amazonaws.com:8081")
    };

    private final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(ProxyServiceApplication.class, args);
    }

    @GetMapping("/collatz")
    public String getLucasSequence(@RequestParam("n") int n) {
        int backendIndex = counter.getAndIncrement() % backendUrls.length;
        String backendUrl = backendUrls[backendIndex] + "/collatz?n=" + n;
        return restTemplate.getForObject(backendUrl, String.class);
    }
}

