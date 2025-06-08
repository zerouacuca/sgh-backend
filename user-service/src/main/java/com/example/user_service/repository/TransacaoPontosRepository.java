package com.example.user_service.repository;

import com.example.user_service.model.TransacaoPontos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoPontosRepository extends JpaRepository<TransacaoPontos, Long> {
    List<TransacaoPontos> findByPacienteIdOrderByDataDesc(Long pacienteId);
}
