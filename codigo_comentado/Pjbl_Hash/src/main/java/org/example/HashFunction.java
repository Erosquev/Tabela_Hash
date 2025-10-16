package org.example;

public class HashFunction {

    // implemento a fubção de Hash modular simples
    public static int hashModularSimples(int chave, int tamanho) {
        if (chave < 0) chave = -chave;
        return chave % tamanho;
    }

    // implemento a função de Hash multiplicativo
    public static int hashMultiplicativo(int chave, int tamanho) {
        if (chave < 0) chave = -chave;
        long chaveLong = chave; // para evitar overflow
        long produto = (long)(chaveLong * 6180339887L);
        produto = produto % 10000000000L; // mantem parte decimal significativa
        return (int)((produto * tamanho) / 10000000000L);
    }

    // implemento a função de Hash duplo para rehashing
    public static int hashDuplo(int chave, int tentativa, int tamanho) {
        if (chave < 0) chave = -chave;
        int h1 = chave % tamanho;
        int h2 = 1 + (chave % (tamanho - 1));
        int resultado = h1 + tentativa * h2;

        // Garanto índice positivo dentro do tamanho da tabela
        resultado = resultado % tamanho;
        if (resultado < 0) resultado += tamanho;

        return resultado;
    }
}
