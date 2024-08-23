package com.example.quadrodehoras;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Equipe {

      int id;
      String jornada;

    private Date dateStart;
    List<Horario> horariosTrabalho = new ArrayList<>();
    List<Horario> horariosFolga = new ArrayList<>();

    public Equipe(int id, String jornada, Date dateStart) {
        this.id = id;
        this.jornada = jornada;
        this.dateStart = dateStart;
    }
}
