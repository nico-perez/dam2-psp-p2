package dev.el_nico.dam2_psp_p2;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public final class App {

    final int numProductores;
    final int accionesPorProductor;

    final int numConsumidores;
    final int accionesPorConsumidor;

    final int numEspaciosEnLaCola;

    final ColaDeTareas cola;

    public App(int numProductores, int accionesPorProductor, int numConsumidores, int accionesPorConsumidor,
            int numEspaciosEnLaCola) {

        if (numProductores < 0 || accionesPorConsumidor < 0 || numConsumidores < 0 || accionesPorConsumidor < 0
                || numEspaciosEnLaCola < 1) {
            throw new IllegalArgumentException("mmmm");
        }

        this.numProductores = numProductores;
        this.accionesPorProductor = accionesPorProductor;
        this.numConsumidores = numConsumidores;
        this.accionesPorConsumidor = accionesPorConsumidor;
        this.numEspaciosEnLaCola = numEspaciosEnLaCola;

        cola = new ColaDeTareas(numEspaciosEnLaCola);
    }

    public void empezar() {
        

        List<Thread> hilos = new ArrayList<>(numProductores + numConsumidores);

        for (int i = 0; i < numProductores; ++i) {
            hilos.add(new Productor(accionesPorProductor, cola));
        }

        for (int i = 0; i < numConsumidores; ++i) {
            hilos.add(new Consumidor(accionesPorConsumidor, cola));
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(cola);
        System.out.println("FIN");
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        new App(20, 1000, 20, 1000, 400).empezar();
    }

    
}
