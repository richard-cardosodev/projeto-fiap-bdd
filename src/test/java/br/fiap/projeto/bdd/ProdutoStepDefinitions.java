package br.fiap.projeto.bdd;

import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Entao;

public class ProdutoStepDefinitions {

    @Quando("Cadastrar-se um produto com o nome {string}, descricao {string}, preco {string}, categoria {string}, imagem {string}, tempoPreparo {string}")
    public void cadastrar_se_um_produto_com_o_nome_descricao_preco_categoria_imagem_tempo_preparo(String string, String string2, String string3, String string4, String string5, String string6) {

    }

    @Quando("O produto for cadastrado com sucesso OU o produto ja existir")
    public void o_produto_for_cadastrado_com_sucesso_ou_o_produto_ja_existir() {

    }
}
