package dev.el_nico.dam2_psp_p2;

import java.util.Objects;

public class Productor extends Thread {

    private static int _ids = 0;
    private final int id = _ids++;

    private final int numVueltas;
    private final ColaDeTareas colaDeTareas;        
    private final StringBuilder sb = new StringBuilder("prod[").append(id).append("] produce ");
    private final int length = sb.length();


    public Productor(int numVueltas, ColaDeTareas colaDeTareas) {

        if (numVueltas < 0) {
            throw new IllegalArgumentException();
        }

        this.numVueltas = numVueltas;
        this.colaDeTareas = Objects.requireNonNull(colaDeTareas);
    }
    
    @Override
    public void run() {
        for (int i = 0; i < numVueltas; ++i) {
            int paraAniadir = (int) (Math.random() * 100) + 1;
            colaDeTareas.anadeTarea(paraAniadir);
            System.out.println(sb.replace(length, Integer.MAX_VALUE, Integer.toString(paraAniadir)));
        }
    }
}
