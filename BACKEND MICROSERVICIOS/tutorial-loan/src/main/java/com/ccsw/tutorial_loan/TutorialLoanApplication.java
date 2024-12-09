package com.ccsw.tutorial_loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
// Habilita Feign y especifica el paquete donde están las interfaces Feign
public class TutorialLoanApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutorialLoanApplication.class, args);
    }
}