package com.example.quadrodehoras;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Horario {
    String turno;
    Date horaInicio;
    Date horaFim;

    public Horario(String turno, Date horaInicio, Date horaFim) {
        this.turno = turno;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    @Override
    public String toString() {
        String myFormat = "dd/MM/yyyy HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        return   this.turno + " - " + sdf.format(horaInicio) + " até " + sdf.format(horaFim);
    }
}