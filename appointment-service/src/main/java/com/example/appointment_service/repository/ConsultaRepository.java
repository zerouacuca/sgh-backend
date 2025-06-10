package com.example.appointment_service.repository;

import com.example.appointment_service.model.Consulta;
import com.example.appointment_service.model.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Buscar por especialidade
    List<Consulta> findByEspecialidadeIgnoreCase(String especialidade);

    // Buscar por médico
    List<Consulta> findByMedicoIgnoreCase(String medico);

    // Buscar por especialidade E data futura
    List<Consulta> findByEspecialidadeIgnoreCaseAndDataHoraAfter(String especialidade, LocalDateTime dataHora);

    // Buscar por médico E data futura
    List<Consulta> findByMedicoIgnoreCaseAndDataHoraAfter(String medico, LocalDateTime dataHora);

    // Buscar todas as consultas futuras com status DISPONIVEL
    List<Consulta> findByDataHoraAfterAndStatus(LocalDateTime dataHora, StatusConsulta status);
}