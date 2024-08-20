package com.example.quadrodehoras;
import java.util.Date;

public class Jornadas {
    int turno;
    int hrTrabalho;
    int hrFolgas;

    public Jornadas(int turno, int hrInicio, int hrTermino) {
        this.turno = turno;
        this.hrTrabalho = hrInicio;
        this.hrFolgas = hrTermino;
    }

    @Override
    public String toString() {
        return "Jornadas{" +
                "turno=" + turno +
                ", hrInicio=" + hrTrabalho +
                ", hrTermino=" + hrFolgas +
                ", dateInicio=" + dateInicio +
                '}';
    }

    Date dateInicio;

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getHrTrabalho() {
        return hrTrabalho;
    }

    public void setHrTrabalho(int hrTrabalho) {
        this.hrTrabalho = hrTrabalho;
    }

    public int getHrFolgas() {
        return hrFolgas;
    }

    public void setHrFolgas(int hrFolgas) {
        this.hrFolgas = hrFolgas;
    }

    public Date getDateInicio() {
        return dateInicio;
    }

    public void setDateInicio(Date dateInicio) {
        this.dateInicio = dateInicio;
    }
}