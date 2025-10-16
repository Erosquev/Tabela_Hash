package org.example;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        // Defino os diferentes tamanhos de tabela e de números de dados
        int[] tamanhosTabela = {150000, 1500000, 15000000};
        int[] numerosDados = {100_000, 1_000_000, 10_000_000};
        long seed = 2025L;

        for (int tamanhoTabela : tamanhosTabela) {
            System.out.println("=== Tabela Hash tamanho: " + tamanhoTabela + " ===");

            //Instancio 3 tabelas diferentes para cada tamanho de tabela: uma para utilizar hash modular simples com encadeamento, e outra para utilizar hash multiplicativo e outrra com rehashing
            TabelaHash tabelaEnc = new TabelaHash(tamanhoTabela);
            TabelaHash tabelaHashMult = new TabelaHash(tamanhoTabela);
            TabelaHash tabelaReh = new TabelaHash(tamanhoTabela);

            //Gero os diferentes conjuntos
            for (int n : numerosDados) {
                System.out.println("\nConjunto de dados: " + n + " registros");

                //Estabeleço o codigo do registro para cada dado
                Registro[] registros = GeradorRegistros.gerarRegistros(n, seed);



                // Inserção //
                //Crio variável inicial para medição do tempo com encadeamento e também variável que contará quantas colisões ocorreram
                long inicioEnc = System.nanoTime();
                long colisoesEnc = 0;

                //Insiro os registros na tabela com hash modular simples e comparo se ja há algum registro naquela posição, caso ja haja, realizo encadeamento e conto como uma colisão
                for (Registro r : registros) {
                    int indice = HashFunction.hashModularSimples(r.getId(), tamanhoTabela);
                    if (tabelaEnc.tabela[indice] != null) colisoesEnc++;
                    tabelaEnc.inserirEncadeamento(r);
                }
                //Crio variável final para medição do tempo com encadeamento
                long endEnc = System.nanoTime();

                //Crio variável inicial para medição do tempo com Rehashing e variável que contará quantas colisões ocorreram
                long inicioHashMult = System.nanoTime();
                long colisoesHashMult = 0;

                //Insiro os registros na tabela utilizando hash multiplicativo e comparo se ja há algum registro naquela posição, caso ja haja, realizo rehashing e conto como uma colisão
                //Crio também uma variável que contrá quantas vezes ocorreu a tentativa de inserir determinado valor de hash
                for (Registro r : registros) {
                    // Escolhe automaticamente o tipo de inserção com base no tamanho da tabela
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
                        // Para tabelas médias e grandes, usa encadeamento
                        tabelaEnc.inserirEncadeamento(r);
                    }
                }
                //Crio variável final para medição do tempo com rehashing
                long finalHashMult = System.nanoTime();


                //Crio variável inicial para medição do tempo com Rehashing e variável que contará quantas colisões ocorreram
                long inicioReh = System.nanoTime();
                long colisoesReh = 0;

                //Insiro os registros na tabela e comparo se ja há algum registro naquela posição, caso ja haja, realizo rehashing e conto como uma colisão
                //Crio também uma variável que contrá quantas vezes ocorreu a tentativa de inserir determinado valor de hash
                for (Registro r : registros) {
                    // Escolhe automaticamente o tipo de inserção com base no tamanho da tabela
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
                //Crio variável final para medição do tempo com rehashing
                long finalReh = System.nanoTime();

                //exibo o tempo final dos diferentes tipos de inserção, juntamente com sua quantidade de colisões
                System.out.println("Encadeamento: Tempo inserção = " + (endEnc- inicioEnc)/1e6 + " ms, Colisões = " + colisoesEnc);
                System.out.println("Hashing Hash Multiplicativo: Tempo inserção = " + (finalHashMult- inicioHashMult)/1e6 + " ms, Colisões = " + colisoesHashMult);
                System.out.println("Rehashing: Tempo inserção = " + (finalReh - inicioReh)/1e6 + " ms, Colisões = " + colisoesReh);


                // Busca //

                //Crio variável inicial para medição do tempo de busca com encadeamento
                long inicioBuscaEnc = System.nanoTime();
                int encontradosEnc = 0;

                //visito de registro em registro, contando-os
                for (Registro r : registros) {
                    if (tabelaEnc.buscarEncadeamento(r.getId())){
                        encontradosEnc++;
//                        System.out.println(r.getId());
                    }

                }
                //Crio variável final para medição do tempo com encadeamento
                long finalBuscaEnc = System.nanoTime();

                //Crio variável inicial para medição do tempo de busca com hashing multiplicativo e outra da quantidade de valores encontrados
                long inicioBuscaMult = System.nanoTime();


                for (Registro r : registros) {
                    if (tabelaReh.buscarMult(r.getId()));
                }
                //Crio variável final para medição do tempo com hashing multiplicativo
                long finalBuscaMult = System.nanoTime();

                //Crio variável inicial para medição do tempo de busca com rehashing e outra da quantidade de valores encontrados
                long inicioBuscaReh = System.nanoTime();

                for (Registro r : registros) {
                    if (tabelaReh.buscarRehashing(r.getId()));
                }
                //Crio variável final para medição do tempo com rehashing
                long finalBuscaReh = System.nanoTime();

                System.out.println("Encadeamento: Tempo busca = " + (finalBuscaEnc - inicioBuscaEnc)/1e6 );
                System.out.println("Hashing Hash Multiplicativo: Tempo busca = " + (finalBuscaMult- inicioBuscaMult)/1e6);
                System.out.println("Rehashing: Tempo busca = " + (finalBuscaReh - inicioBuscaReh)/1e6);

                // Estatísticas //
                int[] top3 = tabelaEnc.maioresListasEncadeadas();
                int[] gaps = tabelaEnc.gaps();
                System.out.println("Maiores listas encadeadas (top 3): " + top3[0] + ", " + top3[1] + ", " + top3[2]);
                System.out.println("Gaps (menor, maior, média): " + gaps[0] + ", " + gaps[1] + ", " + gaps[2]);
            }

            System.out.println("\n------------------------------\n");
        }
    }

}
