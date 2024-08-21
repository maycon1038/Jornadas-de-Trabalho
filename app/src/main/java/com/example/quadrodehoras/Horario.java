package com.example.quadrodehoras;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Horario {
      int turno;
      Date horaInicio;
      Date horaFim;

      String nomeTurno;

    public Horario(String nomeTurno, int turno, Date horaInicio, Date horaFim) {
        this.turno = turno;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.nomeTurno = nomeTurno;
    }

    @Override
    public String toString() {
        String myFormat = "dd/MM/yyyy HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        return   this.nomeTurno + " - " + sdf.format(horaInicio) + " até " + sdf.format(horaFim);
    }
}