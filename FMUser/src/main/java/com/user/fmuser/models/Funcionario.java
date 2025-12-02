package com.user.fmuser.models;

public class Funcionario {
    public String nome;
    public String sobrenome;
    public boolean isMotorista;
    private String cpf;

    public Funcionario(String cpf, String nome, String sobrenome, boolean isMotorista) {
        if (!this.setCpf(cpf)) {
            throw new IllegalArgumentException("Invalid CPF when constructing Funcionario");
        }
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.isMotorista = isMotorista;
    }

    public String getCpf() {
        return cpf;
    }

    /**
     * Sets the CPF of the employee object. If the constraint of exactly 11 digits isn't
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

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public boolean isMotorista() {
        return isMotorista;
    }

    public void setMotorista(boolean isMotorista) {
        this.isMotorista = isMotorista;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}
