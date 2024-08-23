package com.example.quadrodehoras;
import java.util.ArrayList;

public class Jornadas {
    String nome;
   ArrayList<Jornada> listJornadas = new ArrayList<>();

    public Jornadas(String nome, ArrayList<Jornada> listJornadas) {
        this.nome = nome;
        this.listJornadas = listJornadas;
    }
}