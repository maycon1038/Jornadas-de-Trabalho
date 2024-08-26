package com.example.quadrodehoras;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Equipe {

      int id;
      String equipe;

    private Date dateStart;
    List<Horario> horariosTrabalho = new ArrayList<>();
    List<Horario> horariosFolga = new ArrayList<>();

    public Equipe(int id, String equipe, Date dateStart) {
        this.id = id;
        this.equipe = equipe;
        this.dateStart = dateStart;
    }
}
