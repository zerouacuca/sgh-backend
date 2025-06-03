package com.example.userservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransacaoPontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origem; // Ex: COMPRA, USO_CONSULTA, CANCELAMENTO
    private String tipo;   // ENTRADA ou SAÍDA
    private String descricao;
    private Integer quantidade;
    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Getters e setters
}
