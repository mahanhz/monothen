package com.example.monothen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
public class MainController {

    @GetMapping("/")
    public Mono<String> index(final WebSession session) {

        return session.changeSessionId()
                      .then(thenTask1())
                      .then(thenTask2())
                      .thenReturn(thenReturnTask());
    }

    @GetMapping("/nested")
    public Mono<String> nested(final WebSession session) {

        return session.changeSessionId()
                      .then(thenTask1().then(thenTask2().thenReturn(thenReturnTask())));
    }

    private Mono<Void> thenTask1() {
        return Mono.create(sink -> {
            System.out.println("Performing then task 1");
            sink.success();
        });
    }

    private Mono<Void> thenTask2() {
        return Mono.create(sink -> {
            System.out.println("Performing then task 2");
            sink.success();
        });
    }

    private String thenReturnTask() {
        System.out.println("Performing then return task");
        return "Welcome to this example";
    }
}
