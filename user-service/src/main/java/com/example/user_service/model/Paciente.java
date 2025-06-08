package com.example.user_service.model;

import jakarta.persistence.*;
import java.util.List;

import lombok.Data;

@Data
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    private String nome;
    private String email;
    private String endereco;

    private Integer saldoPontos = 0;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompraDePontos> compras;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransacaoPontos> transacoes;

    // Getters e setters
}
