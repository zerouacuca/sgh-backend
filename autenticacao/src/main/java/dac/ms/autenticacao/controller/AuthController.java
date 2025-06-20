package dac.ms.autenticacao.controller;

import dac.ms.autenticacao.dto.LoginDTO;
import dac.ms.autenticacao.dto.UsuarioDTO;
import dac.ms.autenticacao.model.Usuario;
import dac.ms.autenticacao.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = authService.cadastrar(usuarioDTO);
        return ResponseEntity.ok(usuario);
    }
}