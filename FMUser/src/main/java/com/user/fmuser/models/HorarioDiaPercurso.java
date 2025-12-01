package com.user.fmuser.models;

import java.sql.Time;

public class HorarioDiaPercurso {
    public int codigo;
    public Time hora;
    public Percurso percurso;
    private String dia;

    public String getDia() {
        return dia;
    }

    /**
     * Sets the weekday for this route.<br>
     * See Database.validDay for details.
     *
     * @param dia Valid weekday.
     * @return true if a valid day was set, false if day was not set.
     */
    public boolean setDia(String dia) {
        if (!Database.validDay(dia)) {
            return false;
        }
        this.dia = dia;
        return true;
    }

    public HorarioDiaPercurso(Time hour, String day, Percurso route) {
        this.hora = hour;
        if (!this.setDia(day)) {
            throw new IllegalArgumentException("Invalid dia when constructing HDP");
        }
        this.percurso = route;
    }

    public HorarioDiaPercurso(int code, Time hour, String day, Percurso route) {
        this.codigo = code;
        this.hora = hour;
        if (!this.setDia(day)) {
            throw new IllegalArgumentException("Invalid dia when constructing HDP");
        }
        this.percurso = route;
    }
}
