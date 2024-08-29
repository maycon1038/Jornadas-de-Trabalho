package com.example.quadrodehoras;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Equipe {


    String equipe;

    String idDocJornadas;

    Date dateStart;

    Jornadas jornadas;

    public Equipe(String idDocJornadas, String equipe, Date dateStart) {
        this.idDocJornadas = idDocJornadas;
        this.equipe = equipe;
        this.dateStart = dateStart;
    }
}
