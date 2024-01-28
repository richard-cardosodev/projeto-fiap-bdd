package br.fiap.projeto.bdd;

import br.fiap.projeto.bdd.utils.DataTransfer;
import br.fiap.projeto.bdd.utils.DataTransferKey;
import br.fiap.projeto.bdd.utils.Variables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.it.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ComandaStepDefinitions {
    private static final String BASE_URL = Variables.CLUSTER_URL;
    private static String responseString;
    @Quando("Solicitar-se a criacao de comanda para o pedido")
    public void solicitar_se_a_criacao_de_comanda_para_o_pedido() throws JsonProcessingException {
        RestAssured.baseURI = BASE_URL;

        String codigoPedido = DataTransfer.getValueAsString(DataTransferKey.CODIGO_PEDIDO);

        Response response = RestAssured.given()
                .pathParam("codigo", codigoPedido)
                .when()
                .patch("/pedido/pedidos/{codigo}/enviar-comanda")
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

    @Quando("A comanda for criada com sucesso")
    public void a_comanda_for_criada_com_sucesso() {
        System.out.println(responseString);
    }

    @Quando("Finalizar-se a comanda do pedido")
    public void finalizar_se_a_comanda_do_pedido() throws JsonProcessingException {
        RestAssured.baseURI = BASE_URL;

        String codigoPedido = DataTransfer.getValueAsString(DataTransferKey.CODIGO_PEDIDO);

        // Prepara a comanda
        Response responsePreparar = RestAssured.given()
                .pathParam("codigo-pedido", codigoPedido)
                .when()
                .patch("/comanda/comanda/{codigo-pedido}/preparar")
                .then()
                .statusCode(200)
                .extract().response();

        int statusCodePreparar = responsePreparar.getStatusCode();
        assertEquals(200, statusCodePreparar);

        // Prepara a comanda
        Response responseFinalizar = RestAssured.given()
                .pathParam("codigo-pedido", codigoPedido)
                .when()
                .patch("/comanda/comanda/{codigo-pedido}/finalizar")
                .then()
                .statusCode(200)
                .extract().response();

        int statusCodeFinalizar = responseFinalizar.getStatusCode();
        assertEquals(200, statusCodeFinalizar);

        Map<String, Object> resultMap = stringJsonToMapStringObject(responseFinalizar.getBody().asString());

        // Exibir a resposta
        responseString = "Resposta da API: " + responseFinalizar.getBody().asString();
    }

    @Quando("A comanda for finalizada com sucesso")
    public void a_comanda_for_finalizada_com_sucesso() {
        System.out.println(responseString);
    }
    private Map<String,Object> stringJsonToMapStringObject(String stringJson) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Map<String, Object> resultMap = om.readValue(stringJson, Map.class);
        return resultMap;
    }
}
