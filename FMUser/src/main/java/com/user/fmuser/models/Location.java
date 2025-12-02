package com.user.fmuser.models;

public class Location {
    public int codigo;

    public String localizacao;

    public enum LocationType {Ciclovia, Parada};

    public Location(String location) {
        this.localizacao = location;
    }

    public Location(int code, String location) {
        this.codigo = code;
        this.localizacao = location;
    }
}
