package com.example.user_service.controller;

import com.example.user_service.dto.PacienteDTO;
import com.example.user_service.dto.TransacaoPontosDTO;
import com.example.user_service.model.Paciente;
import com.example.user_service.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // [GET] /users
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    // [POST] /users
    @PostMapping
    public ResponseEntity<Paciente> criarPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.salvarPaciente(paciente));
    }

    // [POST] /users/{id}/comprar-pontos
    @PostMapping("/{id}/comprar-pontos")
    public ResponseEntity<Paciente> comprarPontos(
            @PathVariable Long id,
            @RequestParam Double valor,
            @RequestParam Integer pontos
    ) {
        Paciente atualizado = pacienteService.registrarCompra(id, valor, pontos);
        return ResponseEntity.ok(atualizado);
    }

    // [GET] /users/{id}/saldo
    @GetMapping("/{id}/saldo")
    public ResponseEntity<Integer> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.consultarSaldo(id));
    }

    // [GET] /users/{id}/transacoes
    @GetMapping("/{id}/transacoes")
    public ResponseEntity<List<TransacaoPontosDTO>> extrato(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.listarTransacoes(id));
    }
}
