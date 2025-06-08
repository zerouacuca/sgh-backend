package com.example.user_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class PacienteDTO {
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String endereco;
    private Integer saldo;
    private List<TransacaoPontosDTO> transacoes;

    // Getters e Setters
}
