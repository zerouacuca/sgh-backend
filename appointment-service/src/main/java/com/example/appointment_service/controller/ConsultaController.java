package com.example.appointment_service.controller;

import com.example.appointment_service.dto.ConsultaRequestDTO;
import com.example.appointment_service.dto.ConsultaResponseDTO;
import com.example.appointment_service.model.Consulta;
import com.example.appointment_service.model.StatusConsulta;
import com.example.appointment_service.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService service;

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> criar(@RequestBody ConsultaRequestDTO dto) {
        Consulta consulta = Consulta.builder()
                .especialidade(dto.getEspecialidade())
                .medico(dto.getMedico())
                .dataHora(dto.getDataHora())
                .valor(dto.getValor())
                .vagas(dto.getVagas())
                .status(StatusConsulta.DISPONIVEL)
                .build();

        Consulta salva = service.salvar(consulta);

        ConsultaResponseDTO response = ConsultaResponseDTO.builder()
                .id(salva.getId())
                .especialidade(salva.getEspecialidade())
                .medico(salva.getMedico())
                .dataHora(salva.getDataHora())
                .valor(salva.getValor())
                .vagas(salva.getVagas())
                .status(salva.getStatus())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizar(@PathVariable Long id, @RequestBody Consulta novaConsulta) {
        return ResponseEntity.ok(service.atualizar(id, novaConsulta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}