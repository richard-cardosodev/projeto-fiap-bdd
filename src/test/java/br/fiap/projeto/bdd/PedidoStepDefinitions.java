package br.fiap.projeto.bdd;

import br.fiap.projeto.bdd.utils.DataTransfer;
import br.fiap.projeto.bdd.utils.DataTransferKey;
import br.fiap.projeto.bdd.utils.Variables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PedidoStepDefinitions {
    private static final String BASE_URL = Variables.CLUSTER_URL;
    private static String responseString;
    @Quando("Criar-se um pedido para o cliente com cpf {string}")
    public void criar_se_um_pedido_para_o_cliente_com_cpf(String string) throws JsonProcessingException {
        RestAssured.baseURI = BASE_URL;

        String codigoCliente = DataTransfer.getValueAsString(DataTransferKey.CODIGO_CLIENTE);

        Response response = RestAssured.given()
                    .queryParam("codigo_cliente", codigoCliente)
                .when()
                    .post("/pedido/pedidos")
                .then()
                    .statusCode(200)
                .extract().response();

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        Map<String, Object> resultMap = stringJsonToMapStringObject(response.getBody().asString());

        DataTransfer.setValue(DataTransferKey.CODIGO_PEDIDO, resultMap.get("codigo").toString());
        // Exibir a resposta
        responseString = "Resposta da API: " + response.getBody().asString();
    }

    @Quando("O pedido for criado com sucesso")
    public void o_pedido_for_criado_com_sucesso() {
        System.out.println(responseString);
    }

    @Quando("Adicionar-se o produto da categoria {string} ao pedido")
    public void adicionar_se_o_produto_da_categoria_ao_pedido(String string) throws JsonProcessingException {
        String codigoPedido = DataTransfer.getValueAsString(DataTransferKey.CODIGO_PEDIDO);
        String codigoProduto = DataTransfer.getValueAsString(DataTransferKey.CODIGO_PRODUTO);
        RestAssured.baseURI = BASE_URL;


        Response response = RestAssured.given()
                .pathParam("codigo_pedido", codigoPedido)
                .pathParam("codigo_produto", codigoProduto)
                .when()
                .post("/pedido/pedidos/{codigo_pedido}/adicionar-produto/{codigo_produto}")
                .then()
                .statusCode(200)
                .extract().response();

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Exibir a resposta
        responseString = "Resposta da API: " + response.getBody().asString();
    }

    @Quando("O pedido for atualizado com sucesso")
    public void o_pedido_for_atualizado_com_sucesso() {
        System.out.println(responseString);
    }

    @Quando("Solicitar-se confirmacao para o pedido criado")
    public void solicitar_se_confirmacao_para_o_pedido_criado() {
        String codigoPedido = DataTransfer.getValueAsString(DataTransferKey.CODIGO_PEDIDO);
        RestAssured.baseURI = BASE_URL;

        Response response = RestAssured.given()
                .pathParam("codigo_pedido", codigoPedido)
                .when()
                .put("/pedido/pedidos/{codigo_pedido}/pagar")
                .then()
                .statusCode(200)
                .extract().response();

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Exibir a resposta
        responseString = "Resposta da API: " + response.getBody().asString();
    }

    @Quando("O pedido for confirmado com sucesso")
    public void o_pedido_for_confirmado_com_sucesso() {
        System.out.println(responseString);
    }

    @Quando("Informar-se que o pedido esta finalizado")
    public void informar_se_que_o_pedido_esta_finalizado() {
//        String codigoPedido = DataTransfer.getValueAsString(DataTransferKey.CODIGO_PEDIDO);
//        RestAssured.baseURI = BASE_URL;
//
//        Response response = RestAssured.given()
//                .pathParam("codigo_pedido", codigoPedido)
//                .when()
//                .put("/pedido/pedidos/{codigo_pedido}/entregar")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        int statusCode = response.getStatusCode();
//        assertEquals(200, statusCode);
//
//        // Exibir a resposta
//        responseString = "Resposta da API: " + response.getBody().asString();
    }

    @Entao("O status do pedido eh atualizado para {string}")
    public void oStatusDoPedidoEhAtualizadoPara(String arg0) {
        System.out.println(responseString);
    }

    private Map<String,Object> stringJsonToMapStringObject(String stringJson) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Map<String, Object> resultMap = om.readValue(stringJson, Map.class);
        return resultMap;
    }
}
