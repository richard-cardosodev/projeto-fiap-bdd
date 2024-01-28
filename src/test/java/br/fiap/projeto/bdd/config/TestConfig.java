package br.fiap.projeto.bdd.config;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class TestConfig {

    @Before
    public void setup() {
        RestAssured.filters(new AuthTokenFilter());
    }
}
