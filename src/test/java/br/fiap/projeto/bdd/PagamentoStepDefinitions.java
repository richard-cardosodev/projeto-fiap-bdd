package br.fiap.projeto.bdd;

import br.fiap.projeto.bdd.utils.DataTransfer;
import br.fiap.projeto.bdd.utils.DataTransferKey;
import br.fiap.projeto.bdd.utils.Variables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.it.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PagamentoStepDefinitions {

    private static final String BASE_URL = Variables.CLUSTER_URL;

    private static final String ENVIA_GATEWAY_PAGAMENTO = "/pagamento/pagamento/gateway/gateway-de-pagamento";
    private static final String RETORNO_GATEWAY_PAGAMENTO = "/pagamento/pagamento/retorno-gateway/atualiza-status";

    private static String STATUS_PAGAMENTO_APROVADO = "APPROVED";

    private String codigoPedido;

    private Response response;
    RequestSpecification requestSpecification;

    @Quando("O pagamento do pedido for confirmado")
    public void o_pagamento_do_pedido_for_confirmado() {
        setup();
        //enviar ao gateway de pagamento
        String pedidoAEnviarAoGatewayAsJsonString = setupPedidoAPagarRequestAsJsonString(codigoPedido);
        response = requestSpecification
                .body(pedidoAEnviarAoGatewayAsJsonString)
                .when()
                .post(ENVIA_GATEWAY_PAGAMENTO)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        //retorno do gateway após pagamento confirmado
        String retornoGatewayPagamentoAprovadoAsJsonString = setupRetornoGatewayPagamentoAprovadoAsJsonString(codigoPedido, STATUS_PAGAMENTO_APROVADO);
        response = requestSpecification
                .body(retornoGatewayPagamentoAprovadoAsJsonString)
                .when()
                .patch(RETORNO_GATEWAY_PAGAMENTO);
    }

    @Quando("O status do pedido for atualizado para Aprovado com sucesso")
    public void o_status_do_pedido_for_atualizado_para_aprovado_com_sucesso() {

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

    }

    void setup(){
        RestAssured.baseURI = BASE_URL;
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        codigoPedido = DataTransfer.getValueAsString(DataTransferKey.CODIGO_PEDIDO);
    }

    private String setupPedidoAPagarRequestAsJsonString(String codigoPedido) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.createObjectNode()
                .put("codigoPedido", codigoPedido)
                .put("dataPagamento", "2024-01-27T11:19:30.032Z")
                .put("valorTotal", "25.45")
                .put("statusPagamento", "IN_PROCESS");
        String json;
        try {
            json = objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private String setupRetornoGatewayPagamentoAprovadoAsJsonString(String codigoPedido, String statusAtualizado) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.createObjectNode()
                .put("codigoPedido", codigoPedido)
                .put("status", statusAtualizado);
        String json;
        try {
            json = objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

}
