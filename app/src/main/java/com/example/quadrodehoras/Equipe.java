package com.example.quadrodehoras;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Equipe {


      String equipe;

      Date dateStart;
    List<Horario> horariosTrabalho = new ArrayList<>();
    List<Horario> horariosFolga = new ArrayList<>();

    public Equipe( String equipe, Date dateStart) {
        this.equipe = equipe;
        this.dateStart = dateStart;
    }
}
