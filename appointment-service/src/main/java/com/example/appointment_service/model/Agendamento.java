package main.java.com.example.appointment_service.model;

import java.math.BigDecimal;

public @Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoUnico;
    private Long pacienteId;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status = StatusAgendamento.CRIADO;

    private boolean checkIn;

    private int pontosUsados;
    private BigDecimal valorComplementar;

    @ManyToOne
    private Consulta consulta;
}