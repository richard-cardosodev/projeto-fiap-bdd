package br.fiap.projeto.bdd.utils.identificacao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClienteResponseDTO {

    private String codigo;
    private String nome;
    private String cpf;
    private String email;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(String codigo, String nome, String cpf, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public ClienteResponseDTO(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }
}
