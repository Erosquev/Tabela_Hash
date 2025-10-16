package org.example;

//Crio a classe Node para ser usada no encadeamento
public class Node {

    private Registro registro;
    private Node proximo;

    public Node(Registro registro) {
        this.registro = registro;
        this.proximo = null;
    }

    public Registro getRegistro() {
        return registro;
    }

    public Node getProximo() {
        return proximo;
    }

    public void setProximo(Node proximo) {
        this.proximo = proximo;
    }
}
