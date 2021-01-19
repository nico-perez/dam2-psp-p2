package dev.el_nico.dam2_psp_p2;

/**
 * Hello world!
 */
public final class Main {
    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        final ConcurrentCircularBuffer<Long> buffer = new ConcurrentCircularBuffer<>(32);
    }
}
