package dac.ms.autenticacao.dto;

public record LoginDTO(String email, String senha) {

    public String getEmail() {
        return this.email;
    } 
}


