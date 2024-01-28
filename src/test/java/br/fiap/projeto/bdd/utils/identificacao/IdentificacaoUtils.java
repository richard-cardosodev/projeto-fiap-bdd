package br.fiap.projeto.bdd.utils;

public class IdentificacaoUtils {

    public static ClienteRequestDTO geraClienteRequestDTO() {
        return ClienteRequestDTO.builder()
                .nome("Cliente1")
                .email(EmailUtil.gerarEmail())
                .cpf(CPFUtil.gerarCPFSoNumeros())
                .build();
    }
}
