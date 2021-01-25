package dev.el_nico.dam2_psp_p2;

import java.util.Objects;

public class Consumidor extends Thread {

    private static int _ids = 0;
    private final int id = _ids++;

    private final int numVueltas;
    private final ColaDeTareas colaDeTareas;        
    private final StringBuilder sb = new StringBuilder("cons[").append(id).append("] consume ");
    private final int length = sb.length();

    public Consumidor(int numVueltas, ColaDeTareas colaDeTareas) {

        if (numVueltas < 0) {
            throw new IllegalArgumentException();
        }

        this.numVueltas = numVueltas;
        this.colaDeTareas = Objects.requireNonNull(colaDeTareas);
    }
    
    @Override
    public void run() {
        for (int i = 0; i < numVueltas; ++i) {
            int retirado =  colaDeTareas.quitaTarea();
            System.out.println(sb.replace(length, Integer.MAX_VALUE, Integer.toString(retirado)));
        }
    }
}
