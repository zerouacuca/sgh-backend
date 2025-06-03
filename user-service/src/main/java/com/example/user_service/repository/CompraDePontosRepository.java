package com.example.userservice.repository;

import com.example.userservice.model.CompraDePontos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraDePontosRepository extends JpaRepository<CompraDePontos, Long> {
    List<CompraDePontos> findByPacienteId(Long pacienteId);
}
