package com.example.userservice.repository;

import com.example.userservice.model.TransacaoPontos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoPontosRepository extends JpaRepository<TransacaoPontos, Long> {
    List<TransacaoPontos> findByPacienteIdOrderByDataDesc(Long pacienteId);
}
