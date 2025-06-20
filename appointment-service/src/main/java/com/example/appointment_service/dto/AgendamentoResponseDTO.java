package com.example.appointment_service.dto;

import com.example.appointment_service.model.StatusAgendamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoResponseDTO {

    private Long id;

    private String codigo;

    private Long consultaId;

    private Long pacienteId;

    private LocalDateTime dataCriacao;

    private StatusAgendamento status;

    private BigDecimal valorPago;
}
