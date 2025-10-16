package org.example;

import java.util.Random;

public class GeradorRegistros {

    public static Registro[] gerarRegistros(int n, long seed) {
        Registro[] registros = new Registro[n];
        Random rand = new Random(seed);
        // Estabeleco o maenor valor do random e o maior para que os códigos tenham todos 9 dígitos
        int min = 100_000_000;
        int max = 999_999_999;

        //gero um valor randomico para cada registro do conjunto de regstros
        for (int i = 0; i < n; i++) {
            int codigo = rand.nextInt((max - min + 1)) + min;
            registros[i] = new Registro(codigo);
        }
        return registros;
    }
}
