package com.example.appointment_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId; // Integração com o microserviço de usuários/pacientes

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago; // Pode ser valor total (pontos convertidos + dinheiro)
}