package dev.el_nico.dam2_psp_p2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class AppTest {

    // Mismos consumidores que productores. En este caso la cola terminará vacia.
    @Test
    public void caso1() {
        App app = new App(10, 1000, 10, 1000, 3000);
        app.empezar();

        assertEquals(0, app.cola.size());
    }

    // Más consumidores que productores. En este caso el programa no finalizará.
    @Test
    public void caso2() {
        App app = new App(2, 1000, 10, 1000, 3000);
        app.empezar();
        fail();
    }

    // Menos consumidores que productores. En este caso la cola tendrá contenido al finalizar la ejecución del programa.
    @Test
    public void caso3() {
        App app = new App(10, 1000, 9, 1000, 3000);
        app.empezar();

        assertEquals(1000, app.cola.size());
    }
}
