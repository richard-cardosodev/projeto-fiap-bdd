package br.fiap.projeto.bdd.utils.identificacao;

public class IdentificacaoUtils {

    public static ClienteRequestDTO geraClienteRequestDTO() {
        return ClienteRequestDTO.builder()
                .nome("Cliente1")
                .email(EmailUtil.gerarEmail())
                .cpf(CPFUtil.gerarCPFSoNumeros())
                .build();
    }
}
