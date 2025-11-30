package com.user.fmuser.models;

public class Usuario {
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean isAdmin;

    /**
     * Sets the CPF of the user object. If the constraint of exactly 11 digits isn't
     * satisfied, no update occurs.
     *
     * @param cpf The CPF to set.
     * @return True if the object was updated, false otherwise.
     */
    public boolean setCpf(String cpf) {
        if (!Database.validCPF(cpf)) {
            return false;
        }
        this.cpf = cpf;
        return true;
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
        if (!this.setCpf(cpf)) {
            throw new IllegalArgumentException("Invalid CPF when constructing Usuario");
        }
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = false;
    }
}

