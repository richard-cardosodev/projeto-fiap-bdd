package br.fiap.projeto.bdd.utils.identificacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    private String nome;
    private String cpf;
    private String email;

}
