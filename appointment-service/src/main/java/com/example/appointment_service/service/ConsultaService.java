package com.example.appointment_service.service;

import com.example.appointment_service.model.Consulta;
import com.example.appointment_service.model.StatusConsulta;
import com.example.appointment_service.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository repository;

    public Consulta salvar(Consulta consulta) {
        return repository.save(consulta);
    }

    public List<Consulta> listarTodas() {
        return repository.findAll();
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Consulta atualizar(Long id, Consulta novaConsulta) {
        return repository.findById(id).map(consulta -> {
            consulta.setEspecialidade(novaConsulta.getEspecialidade());
            consulta.setMedico(novaConsulta.getMedico());
            consulta.setDataHora(novaConsulta.getDataHora());
            consulta.setValor(novaConsulta.getValor());
            consulta.setVagas(novaConsulta.getVagas());
            consulta.setStatus(novaConsulta.getStatus());
            return repository.save(consulta);
        }).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    // 🔍 Buscar por especialidade com data futura
    public List<Consulta> buscarFuturasPorEspecialidade(String especialidade) {
        return repository.findByEspecialidadeIgnoreCaseAndDataHoraAfter(especialidade, LocalDateTime.now());
    }

    // 🔍 Buscar por médico com data futura
    public List<Consulta> buscarFuturasPorMedico(String medico) {
        return repository.findByMedicoIgnoreCaseAndDataHoraAfter(medico, LocalDateTime.now());
    }

    // 🔍 Buscar todas futuras e com status DISPONIVEL
    public List<Consulta> buscarDisponiveisFuturas() {
        return repository.findByDataHoraAfterAndStatus(LocalDateTime.now(), StatusConsulta.DISPONIVEL);
    }
}