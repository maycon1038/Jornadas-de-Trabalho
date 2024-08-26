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

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }


    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public ArrayList<Jornada> getListJornadas() {
        return listJornadas;
    }

    public void setListJornadas(ArrayList<Jornada> listJornadas) {
        this.listJornadas = listJornadas;
    }

    public Jornadas(String name, String equipe, Date dataInicio, ArrayList<Jornada> listJornadas) {
        this.idDoc =  GeradorIdDoc.gerarIdDocUnico();
        this.name = name;
        this.equipe = equipe;
        this.dataInicio = dataInicio;
        this.listJornadas = listJornadas;
    }
}