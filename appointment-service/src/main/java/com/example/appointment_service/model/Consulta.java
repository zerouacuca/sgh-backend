package main.java.com.example.appointment_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String especialidade;
    private String medico;
    private LocalDateTime dataHora;
    private BigDecimal valor;
    private int vagas;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status = StatusConsulta.DISPONIVEL;
}
