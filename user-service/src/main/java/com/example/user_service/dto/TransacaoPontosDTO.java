package com.example.user_service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransacaoPontosDTO {
    private String origem;
    private String tipo; // ENTRADA ou SAIDA
    private LocalDateTime data;
    private Integer valor;
    
    // Getters e Setters
}
