package com.user.fmuser.models;

public class Usuario {
    public String nome;
    public String sobrenome;
    public String email;
    public String senha;
    private String cpf;
    private boolean isAdmin;

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

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCPF() {
        return cpf;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

