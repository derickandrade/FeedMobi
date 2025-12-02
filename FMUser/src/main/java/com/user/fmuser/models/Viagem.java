package com.user.fmuser.models;

public class Viagem {
    public int codigo;
    public Funcionario motorista;
    public Funcionario cobrador;
    public HorarioDiaPercurso horarioDiaPercurso;
    public Veiculo veiculo;

    public Viagem(Funcionario motorista, Funcionario cobrador, HorarioDiaPercurso hdp, Veiculo veiculo) {
        this.motorista = motorista;
        this.cobrador = cobrador;
        this.horarioDiaPercurso = hdp;
        this.veiculo = veiculo;
    }

    public Viagem(int codigo, Funcionario motorista, Funcionario cobrador, HorarioDiaPercurso hdp, Veiculo veiculo) {
        this.motorista = motorista;
        this.cobrador = cobrador;
        this.horarioDiaPercurso = hdp;
        this.veiculo = veiculo;
        this.codigo = codigo;
    }
}
