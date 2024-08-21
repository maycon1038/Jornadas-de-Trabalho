package com.example.quadrodehoras;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Datas {



    private Date dataTimeInicio;

    private String jornada;



    private Date dataTimeFim;

    public Date getDataTimeInicio() {
        return dataTimeInicio;
    }

    public void setDataTimeInicio(Date dataTimeInicio) {
        this.dataTimeInicio = dataTimeInicio;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public Date getDataTimeFim() {
        return dataTimeFim;
    }

    public void setDataTimeFim(Date dataTimeFim) {
        this.dataTimeFim = dataTimeFim;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    private int turno;

    public Datas(String jornada, int turno, String equipe) {
        this.jornada = jornada;
        this.turno = turno;
        this.equipe = equipe;
    }

    private String equipe;

    public Datas() {
    }

    @Override
    public String toString() {
        String myFormat = "dd/MM/yyyy HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        return   this.turno + " - " + sdf.format(dataTimeInicio) + " até " + sdf.format(dataTimeFim);
    }
}
