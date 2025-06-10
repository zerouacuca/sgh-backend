package com.example.appointment_service.dto;

import com.example.appointment_service.model.StatusConsulta;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaResponseDTO {
    private Long id;
    private String especialidade;
    private String medico;
    private LocalDateTime dataHora;
    private BigDecimal valor;
    private int vagas;
    private StatusConsulta status;
}