package dac.ms.autenticacao.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha; // Será criptografada

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo; // PACIENTE ou FUNCIONARIO

    // Ex: ["ROLE_PACIENTE"]
    public List<String> getRoles() {
        return Collections.singletonList("ROLE_" + this.tipo.name());
    }
}
