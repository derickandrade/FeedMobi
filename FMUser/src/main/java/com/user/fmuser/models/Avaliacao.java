package com.user.fmuser.models;

public class Avaliacao {
    public TargetType tipoAlvo;
    public int codigoAlvo; // Rating target's code
    public int nota;
    public String texto;
    public String cpfUsuario;
    public enum TargetType {Ciclovia, Parada, Viagem}

    public Avaliacao(int targetCode, TargetType targetType, int rating, String text, String userCpf) {
        this.codigoAlvo = targetCode;
        this.tipoAlvo = targetType;
        this.nota = rating;
        this.texto = text;
        this.cpfUsuario = userCpf;
    }
}
