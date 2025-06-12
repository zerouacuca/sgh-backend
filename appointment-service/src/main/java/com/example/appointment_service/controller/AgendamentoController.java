package com.example.appointment_service.controller;

import com.example.appointment_service.dto.AgendamentoRequestDTO;
import com.example.appointment_service.dto.AgendamentoResponseDTO;
import com.example.appointment_service.model.Agendamento;
import com.example.appointment_service.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> agendar(@RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = service.agendarConsulta(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<Void> checkIn(@PathVariable Long id) {
        service.checkIn(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comparecimento")
    public ResponseEntity<Void> confirmarComparecimento(@PathVariable Long id) {
        service.confirmarComparecimento(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/resultado")
    public ResponseEntity<Void> registrarResultado(@PathVariable Long id,
                                                   @RequestParam boolean compareceu) {
        service.registrarResultado(id, compareceu);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelarAgendamento(id);
        return ResponseEntity.ok().build();
    }
}