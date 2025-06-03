package com.example.userservice.service;

import com.example.userservice.model.*;
import com.example.userservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CompraDePontosRepository compraRepository;

    @Autowired
    private TransacaoPontosRepository transacaoRepository;

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente salvarPaciente(Paciente paciente) {
        paciente.setSaldoPontos(0); // Inicializa com 0 pontos
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
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

    public List<TransacaoPontos> listarTransacoes(Long pacienteId) {
        return transacaoRepository.findByPacienteIdOrderByDataDesc(pacienteId);
    }
}
