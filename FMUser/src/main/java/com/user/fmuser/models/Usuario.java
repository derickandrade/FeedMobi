package com.user.fmuser.models;

public class Usuario {
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean isAdmin;

    public void setCpf(String cpf) {
        if (cpf.length() != 11) {
            return;
        }
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCPF() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Usuario(String cpf, String nome, String sobrenome, String email, String senha) {
        setCpf(cpf);
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = false;
    }
}

