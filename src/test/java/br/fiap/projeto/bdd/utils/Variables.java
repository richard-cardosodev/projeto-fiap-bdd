package br.fiap.projeto.bdd.utils;

public class Variables {

    public static final String CLUSTER_URL;

    static {

        CLUSTER_URL = System.getenv("PROJETO_FIAP_CLUSTER_URL");
    }
}