package br.fiap.projeto.bdd.config;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class TestConfig {

    @Before
    public void setup() {
        System.out.println("Configurando filtro de autenticação");
        RestAssured.filters(new AuthTokenFilter());
    }
}
