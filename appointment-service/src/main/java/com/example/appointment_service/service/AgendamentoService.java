package com.example.appointment_service.service;

import com.example.appointment_service.dto.AgendamentoRequestDTO;
import com.example.appointment_service.dto.AgendamentoResponseDTO;
import com.example.appointment_service.model.Agendamento;
import com.example.appointment_service.model.Consulta;
import com.example.appointment_service.model.StatusAgendamento;
import com.example.appointment_service.repository.AgendamentoRepository;
import com.example.appointment_service.repository.ConsultaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ConsultaRepository consultaRepository;

    /**
     * Cria um novo agendamento com verificação de vagas disponíveis.
     */
    public AgendamentoResponseDTO agendarConsulta(AgendamentoRequestDTO dto) {
        Consulta consulta = consultaRepository.findById(dto.getConsultaId())
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));

        long agendamentosAtuais = agendamentoRepository.findByConsulta(consulta).size();

        if (agendamentosAtuais >= consulta.getVagas()) {
            throw new IllegalStateException("Não há vagas disponíveis para essa consulta.");
        }

        // Criação do agendamento
        Agendamento agendamento = Agendamento.builder()
                .consulta(consulta)
                .pacienteId(dto.getPacienteId())
                .codigo(UUID.randomUUID().toString())
                .dataCriacao(LocalDateTime.now())
                .status(StatusAgendamento.CRIADO)
                .valorPago(dto.getValorPago() != null ? dto.getValorPago() : BigDecimal.ZERO)
                .build();

        Agendamento salvo = agendamentoRepository.save(agendamento);

        return AgendamentoResponseDTO.builder()
                .id(salvo.getId())
                .codigo(salvo.getCodigo())
                .consultaId(salvo.getConsulta().getId())
                .pacienteId(salvo.getPacienteId())
                .dataCriacao(salvo.getDataCriacao())
                .status(salvo.getStatus())
                .valorPago(salvo.getValorPago())
                .build();
    }

    public List<AgendamentoResponseDTO> listarTodosDTO() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        return agendamentos.stream().map(a -> AgendamentoResponseDTO.builder()
                .id(a.getId())
                .codigo(a.getCodigo())
                .consultaId(a.getConsulta() != null ? a.getConsulta().getId() : null)
                .pacienteId(a.getPacienteId())
                .valorPago(a.getValorPago())
                .status(a.getStatus())
                .build()
        ).collect(Collectors.toList());
    }


    /**
     * Cancela um agendamento (pelo paciente).
     */
    public void cancelarAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);
    }

    /**
     * Realiza check-in do paciente.
     */
    public void checkIn(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        agendamento.setStatus(StatusAgendamento.CHECK_IN);
        agendamentoRepository.save(agendamento);
    }

    /**
     * Confirma comparecimento.
     */
    public void confirmarComparecimento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        agendamento.setStatus(StatusAgendamento.COMPARECEU);
        agendamentoRepository.save(agendamento);
    }

    /**
     * Registra realização da consulta.
     */
    public void registrarResultado(Long id, boolean compareceu) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        agendamento.setStatus(compareceu ? StatusAgendamento.REALIZADO : StatusAgendamento.FALTOU);
        agendamentoRepository.save(agendamento);
    }
}