package dac.ms.autenticacao.service;

import dac.ms.autenticacao.dto.LoginDTO;
import dac.ms.autenticacao.dto.UsuarioDTO;
import dac.ms.autenticacao.model.Usuario;
import dac.ms.autenticacao.model.TipoUsuario;
import dac.ms.autenticacao.repository.UsuarioRepository;
import dac.ms.autenticacao.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UsuarioRepository usuarioRepository,
                     PasswordEncoder passwordEncoder,
                     JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginDTO loginDTO) {
        // 1. Busca usuário manualmente
        System.out.println("Tentativa de login com: " + loginDTO.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.email())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Valida senha manualmente
        if (!passwordEncoder.matches(loginDTO.senha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        // 3. Gera token sem depender do AuthenticationManager
        return jwtTokenProvider.generateToken(usuario.getEmail(),  usuario.getRoles());
    }

    public Usuario cadastrar(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setCpf(usuarioDTO.cpf());
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
        usuario.setTipo(TipoUsuario.valueOf(usuarioDTO.tipo()));
        
        return usuarioRepository.save(usuario);
    }
}