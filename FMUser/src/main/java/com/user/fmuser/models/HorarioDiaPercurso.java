package com.user.fmuser.models;

import java.sql.Time;

public class HorarioDiaPercurso {
    public Time horario;
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
}
