package com.example.appointment_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaRequestDTO {
    private String especialidade;
    private String medico;
    private LocalDateTime dataHora;
    private BigDecimal valor;
    private int vagas;
}
