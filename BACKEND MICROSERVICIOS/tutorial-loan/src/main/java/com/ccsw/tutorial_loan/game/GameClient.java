package com.ccsw.tutorial_loan.game;

import com.ccsw.tutorial_loan.game.model.GameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-GAME", url = "http://localhost:8080")
public interface GameClient {

    @GetMapping(value = "/game")
    List<GameDto> findAll();

    @GetMapping(value = "/game/{id}")
    GameDto findGameById(@PathVariable("id") Long id);
}