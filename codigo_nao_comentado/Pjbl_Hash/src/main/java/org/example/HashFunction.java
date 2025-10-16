package org.example;

public class HashFunction {

    public static int hashModularSimples(int chave, int tamanho) {
        if (chave < 0) chave = -chave;
        return chave % tamanho;
    }

    public static int hashMultiplicativo(int chave, int tamanho) {
        if (chave < 0) chave = -chave;
        long chaveLong = chave;
        long produto = (long)(chaveLong * 6180339887L);
        produto = produto % 10000000000L;
        return (int)((produto * tamanho) / 10000000000L);
    }

    public static int hashDuplo(int chave, int tentativa, int tamanho) {
        if (chave < 0) chave = -chave;
        int h1 = chave % tamanho;
        int h2 = 1 + (chave % (tamanho - 1));
        int resultado = h1 + tentativa * h2;

        resultado = resultado % tamanho;
        if (resultado < 0) resultado += tamanho;

        return resultado;
    }
}
