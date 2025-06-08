package com.example.user_service.repository;

import com.example.user_service.model.CompraDePontos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraDePontosRepository extends JpaRepository<CompraDePontos, Long> {
    List<CompraDePontos> findByPacienteId(Long pacienteId);
}
