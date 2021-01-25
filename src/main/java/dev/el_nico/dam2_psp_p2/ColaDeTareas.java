package dev.el_nico.dam2_psp_p2;

import java.util.concurrent.BlockingQueue;

public class ColaDeTareas {
    
    private final BlockingQueue<Integer> listaDeTareas;

    public ColaDeTareas(int numHuecos) {
        listaDeTareas = new ConcurrentCircularBuffer<>(numHuecos);
    }

    public void anadeTarea(int tarea) {
        try {
            listaDeTareas.put(tarea);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public Integer quitaTarea() {
        try {
            return listaDeTareas.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int size() {
        return listaDeTareas.size();
    }
}
