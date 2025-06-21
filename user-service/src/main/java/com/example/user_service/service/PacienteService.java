package com.example.user_service.service;

import com.example.user_service.dto.PacienteDTO;
import com.example.user_service.dto.TransacaoPontosDTO;
import com.example.user_service.model.*;
import com.example.user_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CompraDePontosRepository compraRepository;

    @Autowired
    private TransacaoPontosRepository transacaoRepository;

    public List<PacienteDTO> listarPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Paciente salvarPaciente(Paciente paciente) {
        paciente.setSaldoPontos(0); // Inicializa com 0 pontos
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Long buscarIdPorEmail(String email) {
        return pacienteRepository.findByEmail(email)
            .map(Paciente::getId)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado com e-mail: " + email));
    }


    public Paciente registrarCompra(Long pacienteId, Double valorReais, Integer pontos) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // 1. Cria e salva a compra
        CompraDePontos compra = new CompraDePontos();
        compra.setPaciente(paciente);
        compra.setValorEmReais(valorReais);
        compra.setQuantidadePontos(pontos);
        compra.setDataCompra(LocalDateTime.now());
        compraRepository.save(compra);

        // 2. Cria transação de entrada
        TransacaoPontos transacao = new TransacaoPontos();
        transacao.setPaciente(paciente);
        transacao.setDescricao("Compra de pontos");
        transacao.setOrigem("COMPRA");
        transacao.setTipo("ENTRADA");
        transacao.setQuantidade(pontos);
        transacao.setData(LocalDateTime.now());
        transacaoRepository.save(transacao);

        // 3. Atualiza saldo
        paciente.setSaldoPontos(paciente.getSaldoPontos() + pontos);
        return pacienteRepository.save(paciente);
    }

    public Integer consultarSaldo(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return paciente.getSaldoPontos();
    }

    public List<TransacaoPontosDTO> listarTransacoes(Long pacienteId) {
        return transacaoRepository.findByPacienteIdOrderByDataDesc(pacienteId)
                .stream()
                .map(this::toTransacaoDTO)
                .collect(Collectors.toList());
    }

    // === Conversão para DTOs ===

    private PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setCpf(paciente.getCpf());
        dto.setNome(paciente.getNome());
        dto.setEmail(paciente.getEmail());
        dto.setEndereco(paciente.getEndereco());
        dto.setSaldo(paciente.getSaldoPontos());

        // Não carrega transações por padrão para evitar recursão
        return dto;
    }

    private TransacaoPontosDTO toTransacaoDTO(TransacaoPontos t) {
        TransacaoPontosDTO dto = new TransacaoPontosDTO();
        dto.setOrigem(t.getOrigem());
        dto.setTipo(t.getTipo());
        dto.setData(t.getData());
        dto.setValor(t.getQuantidade());
        return dto;
    }
}
