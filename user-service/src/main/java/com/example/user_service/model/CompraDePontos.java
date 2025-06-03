package com.example.userservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CompraDePontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorEmReais;
    private Integer quantidadePontos;
    private LocalDateTime dataCompra = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Getters e setters
}
