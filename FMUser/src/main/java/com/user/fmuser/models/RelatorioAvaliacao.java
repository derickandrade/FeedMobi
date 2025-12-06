package com.user.fmuser.models;

public class RelatorioAvaliacao {
    private int codigo;
    private String texto;
    private int nota;
    private String usuario;
    private String tipoAvaliacao;
    private int codigoId;

    public RelatorioAvaliacao(int codigo, String texto, int nota, String usuario,
                              String tipoAvaliacao, int codigoId) {
        this.codigo = codigo;
        this.texto = texto;
        this.nota = nota;
        this.usuario = usuario;
        this.tipoAvaliacao = tipoAvaliacao;
        this.codigoId = codigoId;
    }

    // Getters necessários para PropertyValueFactory
    public int getCodigo() {
        return codigo;
    }

    public String getTexto() {
        return texto;
    }

    public int getNota() {
        return nota;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public int getCodigoId() {
        return codigoId;
    }

    // Setters
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public void setCodigoId(int codigoId) {
        this.codigoId = codigoId;
    }
}
