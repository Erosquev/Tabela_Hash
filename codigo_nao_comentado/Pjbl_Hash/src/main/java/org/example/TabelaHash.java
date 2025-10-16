package org.example;

public class TabelaHash {

    Node[] tabela;
    private int tamanho;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new Node[tamanho];
    }

    public void inserirEncadeamento(Registro reg) {
        int indice = HashFunction.hashModularSimples(reg.getId(), tamanho);
        reg.setCodigoHash(indice);
        Node novo = new Node(reg);
        novo.setProximo(tabela[indice]);
        tabela[indice] = novo;
    }

    public void inserirRehashing(Registro reg) {
        int tentativa = 0;
        int indice;
        do {
            indice = HashFunction.hashDuplo(reg.getId(), tentativa, tamanho);
            tentativa++;
        } while (tabela[indice] != null);
        reg.setCodigoHash(indice);
        tabela[indice] = new Node(reg);
    }

    public boolean buscarEncadeamento(int id) {
        int indice = HashFunction.hashModularSimples(id, tamanho);
        Node atual = tabela[indice];
        while (atual != null) {
            if (atual.getRegistro().getId() == id) return true;
            atual = atual.getProximo();
        }
        return false;
    }

    public boolean buscarMult(int id) {
        int tentativa = 0;
        int indice;
        while (tentativa < tamanho) {
            indice = HashFunction.hashMultiplicativo(id, tamanho);
            Node node = tabela[indice];
            if (node == null) return false;
            if (node.getRegistro().getId() == id) return true;
            tentativa++;
        }
        return false;
    }
    public boolean buscarRehashing(int id) {
        int tentativa = 0;
        int indice;
        while (tentativa < tamanho) {
            indice = HashFunction.hashDuplo(id, tentativa, tamanho);
            Node node = tabela[indice];
            if (node == null) return false;
            if (node.getRegistro().getId() == id) return true;
            tentativa++;
        }
        return false;
    }

    public int[] maioresListasEncadeadas() {
        int[] contagem = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            int tamanhoLista = 0;
            Node atual = tabela[i];
            while (atual != null) {
                tamanhoLista++;
                atual = atual.getProximo();
            }
            contagem[i] = tamanhoLista;
        }
        int[] top3 = {0,0,0};
        for (int c : contagem) {
            if (c > top3[0]) { top3[2]=top3[1]; top3[1]=top3[0]; top3[0]=c; }
            else if (c > top3[1]) { top3[2]=top3[1]; top3[1]=c; }
            else if (c > top3[2]) { top3[2]=c; }
        }
        return top3;
    }

    public int[] gaps() {
        int menor = Integer.MAX_VALUE;
        int maior = Integer.MIN_VALUE;
        long soma = 0;
        int count = 0;
        int atualGap = 0;

        for (Node n : tabela) {
            if (n == null) {
                atualGap++;
            } else {
                if (atualGap > 0) {
                    if (atualGap < menor) menor = atualGap;
                    if (atualGap > maior) maior = atualGap;
                    soma += atualGap;
                    count++;
                }
                atualGap = 0;
            }
        }

        if (atualGap > 0) {
            if (atualGap < menor) menor = atualGap;
            if (atualGap > maior) maior = atualGap;
            soma += atualGap;
            count++;
        }

        if (count == 0) {
            menor = 0;
            maior = 0;
        }

        int media = count > 0 ? (int) (soma / count) : 0;
        return new int[]{menor, maior, media};
    }

}
