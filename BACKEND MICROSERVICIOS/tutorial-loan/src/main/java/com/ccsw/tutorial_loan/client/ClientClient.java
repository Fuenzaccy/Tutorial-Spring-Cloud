package com.ccsw.tutorial_loan.client;

import com.ccsw.tutorial_loan.client.model.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SPRING-CLOUD-EUREKA-CLIENT-CLIENT", url = "http://localhost:8080")
public interface ClientClient {

    @GetMapping(value = "/client")
    List<ClientDto> findAll();

    @GetMapping(value = "/client/{id}")
    ClientDto findClientById(@PathVariable("id") Long id);
}