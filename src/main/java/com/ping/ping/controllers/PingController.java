package com.ping.ping.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    private final WebClient webClient;

    public PingController(@Value("${pong.microservice.url}") String pongBaseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(pongBaseUrl)
                .build();
    }

    @GetMapping
    public Mono<String> ping() {
        return webClient.get()
                .uri("/api/pong")
                .retrieve()
                .bodyToMono(String.class)
                .map(pongResponse -> "Ping -> " + pongResponse);
    }
}
