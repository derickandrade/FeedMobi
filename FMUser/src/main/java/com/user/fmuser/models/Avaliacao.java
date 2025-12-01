package com.user.fmuser.models;

public class Avaliacao {
    public enum TargetType {Ciclovia, Parada, Viagem};
    public TargetType tipoAlvo;
    public int codigoAlvo; // Rating target's code
    public String texto;
    public String cpfUsuario;
}
