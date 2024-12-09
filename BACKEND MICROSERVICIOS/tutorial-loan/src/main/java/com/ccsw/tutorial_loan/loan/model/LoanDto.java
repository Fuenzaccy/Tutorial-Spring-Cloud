package com.ccsw.tutorial_loan.loan.model;

import com.ccsw.tutorial_loan.client.model.ClientDto;
import com.ccsw.tutorial_loan.game.model.GameDto;

import java.time.LocalDate;

public class LoanDto {

    private Long id;
    private ClientDto client;
    private GameDto game;
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}