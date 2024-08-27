package com.example.quadrodehoras;
import java.util.ArrayList;
import java.util.Date;

public class Jornadas {

    private String idDoc;

    private String name;
    private String equipe;

    private Date dataInicio;;
   ArrayList<Jornada> listJornadas = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getDataInicio() {
        return dataInicio;
    }


    public Jornadas(String name, String equipe, Date dataInicio, ArrayList<Jornada> listJornadas) {
        this.idDoc =  GeradorIdDoc.gerarIdDocUnico();
        this.name = name;
        this.equipe = equipe;
        this.dataInicio = dataInicio;
        this.listJornadas = listJornadas;
    }
}