package com.user.fmuser.models;

import java.sql.Date;

public class Veiculo {
    public int numero;
    public Date dataValidade;
    public int assentos;
    public int capacidadeEmPe;
    private String placa;

    public Veiculo(int numero, Date dataValidade, int assentos, int capacidadeEmPe) {
        this.numero = numero;
        this.dataValidade = dataValidade;
        this.assentos = assentos;
        this.capacidadeEmPe = capacidadeEmPe;
        this.placa = null;
    }

    public Veiculo(int numero, Date dataValidade, int assentos, int capacidadeEmPe, String placa) {
        this.numero = numero;
        this.dataValidade = dataValidade;
        this.assentos = assentos;
        this.capacidadeEmPe = capacidadeEmPe;
        if (!this.setPlaca(placa)) {
            throw new IllegalArgumentException("Invalid plate when constructing Veiculo");
        }
    }

    /**
     * Retrieve bus plate.
     *
     * @return Bus plate
     */
    public String getPlaca() {
        return this.placa;
    }

    /**
     * Sets the bus plate.
     *
     * @param plate Plate to set.
     * @return true if a valid plate was provided and set, false otherwise.
     */
    public boolean setPlaca(String plate) {
        if (plate != null && !Database.validPlate(plate)) {
            return false;
        }
        this.placa = plate;
        return true;
    }
}

