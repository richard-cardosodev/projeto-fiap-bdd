package br.fiap.projeto.bdd;

import br.fiap.projeto.bdd.utils.DataTransfer;
import br.fiap.projeto.bdd.utils.DataTransferKey;
import br.fiap.projeto.bdd.utils.Variables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.it.Quando;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProdutoStepDefinitions {

    private Response response;

    @Quando("Cadastrar-se um produto com o nome {string}, descricao {string}, preco {string}, categoria {string}, imagem {string}, tempoPreparo {string}")
    public void cadastrar_se_um_produto_com_o_nome_descricao_preco_categoria_imagem_tempo_preparo(String string, String string2, String string3, String string4, String string5, String string6) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.createObjectNode()
                .put("nome", string)
                .put("descricao", string2)
                .put("preco", string3)
                .put("categoria", string4)
                .put("imagem", string5)
                .put("tempoPreparoMin", string6);
        String json;
        try {
            json = objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response = given()
                .contentType("application/json")
                .body(json)
                .when()
                .post(Variables.CLUSTER_URL + "/produto/produtos");
    }

    @Quando("O produto for cadastrado com sucesso OU o produto ja existir")
    public void o_produto_for_cadastrado_com_sucesso_ou_o_produto_ja_existir() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = response.then()
                .extract().body().asString();
        try {
            Map<String, Object> meuMap = objectMapper.readValue(json, Map.class);
            Assertions.assertTrue(meuMap.get("codigo") != null);
            DataTransfer.setValue(DataTransferKey.CODIGO_PRODUTO, meuMap.get("codigo"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
