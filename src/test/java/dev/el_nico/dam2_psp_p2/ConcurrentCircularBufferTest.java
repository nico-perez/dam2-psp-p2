package dev.el_nico.dam2_psp_p2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;

public class ConcurrentCircularBufferTest {

    @Test
    public void testParaVerSiSeLlenaYDespuesVaciaEnteraLaCola() {
        final List<Thread> listaHilos = new ArrayList<>(40);
        final BlockingQueue<Long> buffer = new ConcurrentCircularBuffer<>(50);

        // 20 hilos que agregan 10000 valores cada uno
        for (int i = 0; i < 20; ++i) {
            listaHilos.add(new Thread(() -> {
                for (int j = 0; j < 10000; ++j) {
                    try {
                        buffer.put((long) j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        // 10 hilos que retiran 20000 valores cada uno
        for (int k = 0; k < 10; ++k) {
            listaHilos.add(new Thread(() -> {
                for (int v = 0; v < 20000; ++v) {
                    try {
                        buffer.take();
                    //    System.out.println("Sacado: " +);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        for (Thread t : listaHilos) {
            t.start();
        }

        for (Thread t : listaHilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(0, buffer.size());
    }

    @Test
    public void testParaComprobarQueElIteradorFunciona() {

        final BlockingQueue<Long> buffer = new ConcurrentCircularBuffer<>(50);

        final int adicionesPlaneadas = (int) (Math.random() * 50) + 51;

        int adicionesReales = 0;
        int retiradasReales = 0;

        for (int i = 0; i < adicionesPlaneadas; ++i) {

            final long paraAniadir = (long) (Math.random() * 100) + 1;

            if (buffer.add(paraAniadir)) {
                adicionesReales++;
                System.out.println(" + " + paraAniadir);
            }
            
            final Long retirado;
            if ((retiradasReales < adicionesPlaneadas / 2) 
                    && (buffer.size() == 50 ? true : Math.random() < .39) 
                    && ((retirado = buffer.poll()) != null)) {
                System.out.println(" - " + retirado);
                retiradasReales++;
            }
        }

        int iteraciones = 0;
        for (long L : buffer) {

            // comprobar que se itera en orden
            assertEquals(buffer.poll(), L);

            System.out.print(L + ", ");
            iteraciones++;
        }
        
        // comprobar que el número de elems del iterador coincide con el de la cola
        assertEquals(adicionesReales - retiradasReales, iteraciones);
    }
}
