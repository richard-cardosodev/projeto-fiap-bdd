package br.fiap.projeto.bdd;

import br.fiap.projeto.bdd.utils.DataTransfer;
import br.fiap.projeto.bdd.utils.Variables;
import br.fiap.projeto.bdd.utils.identificacao.ClienteResponseDTO;
import io.cucumber.java.it.Quando;
import io.restassured.response.Response;

import static br.fiap.projeto.bdd.utils.DataTransferKey.CODIGO_CLIENTE;
import static br.fiap.projeto.bdd.utils.Variables.APPLICATION_JSON_VALUE;
import static br.fiap.projeto.bdd.utils.identificacao.IdentificacaoUtils.geraClienteRequestDTO;
import static io.restassured.RestAssured.given;

public class IdentificacaoStepDefinitions {

    private static final String IDENTIFICACAO_URL = Variables.CLUSTER_URL + "/prod/identificacao/clientes";

    private Response response;

    @Quando("Cadastrar-se um cliente com o nome {string} e o cpf {string}")
    public void cadastrar_se_um_cliente_com_o_nome_e_o_cpf(String string, String string2) {

        System.out.println("Url de identificacao: " + IDENTIFICACAO_URL);

        response = given()
                .contentType(APPLICATION_JSON_VALUE)
                .body(geraClienteRequestDTO())
                .when()
                .post(IDENTIFICACAO_URL);

    }

    @Quando("O cliente for cadastrado com sucesso OU o cliente ja existir")
    public void o_cliente_for_cadastrado_com_sucesso_ou_o_cliente_ja_existir() {

        ClienteResponseDTO dto = response
                .then()
                .statusCode(201)
                .extract().as(ClienteResponseDTO.class);

        DataTransfer.setValue(CODIGO_CLIENTE, dto.getCodigo());
    }
}
