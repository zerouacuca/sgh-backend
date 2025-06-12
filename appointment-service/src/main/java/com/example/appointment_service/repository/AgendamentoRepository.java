package com.example.appointment_service.repository;

import com.example.appointment_service.model.Agendamento;
import com.example.appointment_service.model.Consulta;
import com.example.appointment_service.model.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Optional<Agendamento> findByCodigo(String codigo);

    List<Agendamento> findByConsulta(Consulta consulta);

    List<Agendamento> findByPacienteId(Long pacienteId);

    List<Agendamento> findByStatus(StatusAgendamento status);
}
