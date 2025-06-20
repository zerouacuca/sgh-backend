package com.example.appointment_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoRequestDTO {

    private Long consultaId;

    private Long pacienteId;

    private BigDecimal valorPago; // Valor que o paciente está pagando (em dinheiro ou pontos convertidos)
}
