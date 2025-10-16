package org.example;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int[] tamanhosTabela = {150000, 1500000, 15000000};
        int[] numerosDados = {100_000, 1_000_000, 10_000_000};
        long seed = 2025L;

        for (int tamanhoTabela : tamanhosTabela) {
            System.out.println("=== Tabela Hash tamanho: " + tamanhoTabela + " ===");

            TabelaHash tabelaEnc = new TabelaHash(tamanhoTabela);
            TabelaHash tabelaHashMult = new TabelaHash(tamanhoTabela);
            TabelaHash tabelaReh = new TabelaHash(tamanhoTabela);

            for (int n : numerosDados) {
                System.out.println("\nConjunto de dados: " + n + " registros");

                Registro[] registros = GeradorRegistros.gerarRegistros(n, seed);



                long inicioEnc = System.nanoTime();
                long colisoesEnc = 0;

                for (Registro r : registros) {
                    int indice = HashFunction.hashModularSimples(r.getId(), tamanhoTabela);
                    if (tabelaEnc.tabela[indice] != null) colisoesEnc++;
                    tabelaEnc.inserirEncadeamento(r);
                }
                long endEnc = System.nanoTime();

                long inicioHashMult = System.nanoTime();
                long colisoesHashMult = 0;

                for (Registro r : registros) {
                    if (tamanhoTabela > registros.length) {
                        int tentativa = 0;
                        int indice;
                        int limite = tamanhoTabela;

                        do {
                            indice = HashFunction.hashMultiplicativo(r.getId(), tamanhoTabela);
                            tentativa++;
                            if (tabelaHashMult.tabela[indice] != null) colisoesHashMult++;
                            if (tentativa >= limite) {
                                break;
                            }
                        } while (tabelaHashMult.tabela[indice] != null);

                        if (tentativa < limite) {
                            tabelaHashMult.inserirRehashing(r);
                        }

                    } else {
                        tabelaEnc.inserirEncadeamento(r);
                    }
                }
                long finalHashMult = System.nanoTime();


                long inicioReh = System.nanoTime();
                long colisoesReh = 0;

                for (Registro r : registros) {
                    if (tamanhoTabela > registros.length) {
                        int tentativa = 0;
                        int indice;
                        int limite = tamanhoTabela;

                        do {
                            indice = HashFunction.hashDuplo(r.getId(), tentativa, tamanhoTabela);
                            tentativa++;
                            if (tabelaReh.tabela[indice] != null) colisoesReh++;
                            if (tentativa >= limite) {
                                break;
                            }
                        } while (tabelaReh.tabela[indice] != null);

                        if (tentativa < limite) {
                            tabelaReh.inserirRehashing(r);
                        }

                    } else {
                        tabelaEnc.inserirEncadeamento(r);
                    }
                }
                long finalReh = System.nanoTime();

                System.out.println("Encadeamento: Tempo inserção = " + (endEnc- inicioEnc)/1e6 + " ms, Colisões = " + colisoesEnc);
                System.out.println("Hashing Hash Multiplicativo: Tempo inserção = " + (finalHashMult- inicioHashMult)/1e6 + " ms, Colisões = " + colisoesHashMult);
                System.out.println("Rehashing: Tempo inserção = " + (finalReh - inicioReh)/1e6 + " ms, Colisões = " + colisoesReh);



                long inicioBuscaEnc = System.nanoTime();
                int encontradosEnc = 0;

                for (Registro r : registros) {
                    if (tabelaEnc.buscarEncadeamento(r.getId())){
                        encontradosEnc++;
                    }

                }
                long finalBuscaEnc = System.nanoTime();

                long inicioBuscaMult = System.nanoTime();


                for (Registro r : registros) {
                    if (tabelaReh.buscarMult(r.getId()));
                }
                long finalBuscaMult = System.nanoTime();

                long inicioBuscaReh = System.nanoTime();

                for (Registro r : registros) {
                    if (tabelaReh.buscarRehashing(r.getId()));
                }
                long finalBuscaReh = System.nanoTime();

                System.out.println("Encadeamento: Tempo busca = " + (finalBuscaEnc - inicioBuscaEnc)/1e6 );
                System.out.println("Hashing Hash Multiplicativo: Tempo busca = " + (finalBuscaMult- inicioBuscaMult)/1e6);
                System.out.println("Rehashing: Tempo busca = " + (finalBuscaReh - inicioBuscaReh)/1e6);

                int[] top3 = tabelaEnc.maioresListasEncadeadas();
                int[] gaps = tabelaEnc.gaps();
                System.out.println("Maiores listas encadeadas (top 3): " + top3[0] + ", " + top3[1] + ", " + top3[2]);
                System.out.println("Gaps (menor, maior, média): " + gaps[0] + ", " + gaps[1] + ", " + gaps[2]);
            }

            System.out.println("\n------------------------------\n");
        }
    }

}
