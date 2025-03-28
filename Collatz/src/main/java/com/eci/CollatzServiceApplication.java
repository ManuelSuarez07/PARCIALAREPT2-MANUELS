package com.eci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CollatzServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollatzServiceApplication.class, args);
    }

    @GetMapping("/collatz")
    public String getCollatzSequence(@RequestParam("n") int n) {
        if (n <= 0) {
            return "{\"error\":\"n must be > 0\"}";
        }
        StringBuilder sb = new StringBuilder("[");
        int current = n;
        sb.append(current);
        while (current != 1) {
            if (current % 2 == 0) {
                current = current / 2;
            } else {
                current = 3 * current + 1;
            }
            sb.append(", ").append(current);
        }
        sb.append("]");
        return String.format("{\"operation\":\"collatz\",\"input\":%d,\"output\":%s}", n, sb.toString());
    }
}



