package org.example;
import java.util.Random;

public class Conjunto {
    private long seed = 2025L;
    int min = 100_000_000;
    int max = 999_999_999;
    private Random random = new Random(seed);
    private int tamanho;
    private Registro[] conjunto;

    public Conjunto(int tamanho){
        this.tamanho = tamanho;
        conjunto = new Registro[tamanho];
        for(int i = 0; i < tamanho; i++){

            conjunto[i] = new Registro(random.nextInt(max-min+1)+min);
        }
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Registro[] getConjunto() {
        return conjunto;
    }

    public void setConjunto(Registro[] conjunto) {
        this.conjunto = conjunto;
    }


    public void imprimirConjunto(){
        for(int i = 0; i < tamanho; i++){
            System.out.println(conjunto[i].getId());
        }
    }
}
