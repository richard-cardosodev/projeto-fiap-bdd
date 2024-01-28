package br.fiap.projeto.bdd.utils.identificacao;

import java.security.SecureRandom;

public class CPFUtil {

    public static final SecureRandom RANDOM = new SecureRandom();

    public static String gerarCPF() {

        // Gera os nove primeiros dígitos do CPF
        int num1 = RANDOM.nextInt(10);
        int num2 = RANDOM.nextInt(10);
        int num3 = RANDOM.nextInt(10);
        int num4 = RANDOM.nextInt(10);
        int num5 = RANDOM.nextInt(10);
        int num6 = RANDOM.nextInt(10);
        int num7 = RANDOM.nextInt(10);
        int num8 = RANDOM.nextInt(10);
        int num9 = RANDOM.nextInt(10);

        // Calcula o primeiro dígito verificador
        int digito1 = calculaDigitoVerificador(num1, num2, num3, num4, num5, num6, num7, num8, num9);

        // Calcula o segundo dígito verificador
        int digito2 = calculaDigitoVerificador(num1, num2, num3, num4, num5, num6, num7, num8, num9, digito1);

        // Retorna o CPF formatado
        return String.format("%d%d%d.%d%d%d.%d%d%d-%d%d", num1, num2, num3, num4, num5, num6, num7, num8, num9, digito1, digito2);
    }

    public static String gerarCPFSoNumeros() {
        return gerarCPF().replace(".","").replace("-", "");
    }

    private static int calculaDigitoVerificador(int... numeros) {
        int soma = 0;
        int multiplicador = 2;

        for (int i = numeros.length - 1; i >= 0; i--) {
            soma += numeros[i] * multiplicador;
            multiplicador++;
        }

        int resto = soma % 11;
        int digito = 11 - resto;

        return (digito >= 10) ? 0 : digito;
    }
}
