package br.fiap.projeto.bdd.utils.identificacao;

import java.security.SecureRandom;
import java.util.Random;

public class EmailUtil {

    private static final String[] DOMINIOS = {"gmail.com", "yahoo.com", "hotmail.com", "example.com"};
    public static final SecureRandom RANDOM = new SecureRandom();

    public static String gerarEmail() {

        // Gera um nome de usuário aleatório
        String usuario = gerarUsuarioAleatorio();

        // Escolhe aleatoriamente um domínio da lista de domínios predefinidos
        String dominio = DOMINIOS[RANDOM.nextInt(DOMINIOS.length)];

        // Retorna o e-mail gerado
        return usuario + "@" + dominio;
    }

    private static String gerarUsuarioAleatorio() {
        // Caracteres possíveis no nome de usuário
        String caracteres = "abcdefghijklmnopqrstuvwxyz1234567890";

        StringBuilder usuario = new StringBuilder();

        // Gera um nome de usuário aleatório com comprimento entre 6 e 12 caracteres
        int comprimentoUsuario = 6 + RANDOM.nextInt(7);
        for (int i = 0; i < comprimentoUsuario; i++) {
            int indiceCaractere = RANDOM.nextInt(caracteres.length());
            usuario.append(caracteres.charAt(indiceCaractere));
        }

        return usuario.toString();
    }
}
