package com.kodilla.course.springhibernatecourse.webflux.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class FluxController {

    /**
     * W sytuacji, gdy używamy modułu Spring Reactive Web domyślny serwer zamieniany jest przez serwer Netty (konkretnie embedded Netty),
     * który bardzo dobrze wspiera programowanie reaktywne.
     */

    @GetMapping(value = "/strings", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> getStrings() {
        return Flux
                .just("a", "b", "c", "d", "e")
                .delayElements(Duration.ofSeconds(2))
                .log();
    }

}
