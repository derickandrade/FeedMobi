package com.user.fmuser.models;

public class Percurso {
    int codigo;
    public Parada origem;
    public Parada destino;

    public Percurso(Parada origin, Parada destination) {
        this.origem = origin;
        this.destino = destination;
    }

    public Percurso (int codigo, Parada origin, Parada destination) {
        this.origem = origin;
        this.destino = destination;
        this.codigo = codigo;
    }
}
