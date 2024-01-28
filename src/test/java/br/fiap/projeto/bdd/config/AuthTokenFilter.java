package br.fiap.projeto.bdd.config;

import br.fiap.projeto.bdd.utils.Variables;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;

public class AuthTokenFilter implements Filter {

    private String authToken;
    private boolean isRetrievingToken = false;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext ctx) {
        if (authToken == null && !isRetrievingToken) {
            authToken = obtainAuthToken();
        }
        if (!isRetrievingToken) {
            requestSpec.header("Authorization", authToken);
        }

        return ctx.next(requestSpec, responseSpec);
    }

    private String obtainAuthToken() {
        isRetrievingToken = true;

        // Define the payload
        String payload = "{\"cpf\": \"01234567890\"}";

        try {
            // Use a separate instance of RestAssured to avoid recursion
            RequestSpecification requestSpecification = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body(payload);

            String response = requestSpecification
                    .post(Variables.CLUSTER_URL + "/prod/auth")
                    .then()
                    .extract()
                    .asString();

            return "Bearer " + JsonPath.from(response).getString("token");
        } finally {
            isRetrievingToken = false;
        }
    }
}
