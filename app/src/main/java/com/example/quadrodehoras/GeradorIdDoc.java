package com.example.quadrodehoras;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeradorIdDoc {

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TAMANHO_ID = 6; // Tamanho do ID desejado
    private static final Set<String> idsGerados = new HashSet<>(); // Conjunto para armazenar IDs gerados

    public static String gerarIdDocUnico() {
        Random random = new Random();
        StringBuilder idBuilder;

        do {
            idBuilder = new StringBuilder();
            for (int i = 0; i < TAMANHO_ID; i++) {
                int index = random.nextInt(CARACTERES.length());
                char caractereAleatorio = CARACTERES.charAt(index);
                idBuilder.append(caractereAleatorio);
            }
        } while (idsGerados.contains(idBuilder.toString())); // Repete até gerar um ID único

        String novoId = idBuilder.toString();
        idsGerados.add(novoId); // Adiciona o novo ID ao conjunto
        return novoId;
    }
}