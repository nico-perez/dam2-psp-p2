package dev.el_nico.dam2_psp_p2;

import org.junit.jupiter.api.Test;

public class ConcurrentCircularBufferTest {
    @Test
    public void test() {
        ConcurrentCircularBuffer<Integer> ccb = new ConcurrentCircularBuffer<>(10);

        for (int i = 0; i < 10; ++i) {
            new Thread(() -> { 
                for (int j = 0; j < 20; ++j) ccb.enqueue((int) (Math.random() * 100));
            }).run();
        }

        for (int i = 0; i < 3)
    }
}
