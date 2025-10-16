package org.example;

public class Registro {

    private int id;
    private int codigoHash;

    public Registro(int id){
        this.id = id;
        this.codigoHash = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoHash() {
        return codigoHash;
    }

    public void setCodigoHash(int codigoHash) {
        this.codigoHash = codigoHash;
    }
}
