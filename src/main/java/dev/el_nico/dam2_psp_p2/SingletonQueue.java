package dev.el_nico.dam2_psp_p2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class SingletonQueue {

    /** SingletonQueue instance */
    private static SingletonQueue sq = init();

    private final Lock LOCK;
    private final Condition NOT_EMPTY;
    private final Condition NOT_FULL;

    private static final int MAX_SIZE = 128;

    private static int firstOccupied = 0;
    private static int lastFree = 0;
    private static long[] q;

    private SingletonQueue() {
        LOCK = new ReentrantLock();
        NOT_EMPTY = LOCK.newCondition();
        NOT_FULL = LOCK.newCondition();
        q = new long[MAX_SIZE];
    }

    private static synchronized SingletonQueue init() {
        return sq == null ? new SingletonQueue() : sq;
    }

    public SingletonQueue getInstance() {
        return sq;
    }

    public void enqueue(long o) {
        sq.LOCK.lock();
        
        sq.LOCK.unlock();
    }

    public long dequeue() {
        long o = 0;
       
        return o;
    }

    public int size() {
        
    }

    public boolean isEmpty() {
        return firstOccupied == lastFree;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("no!");
    }
}
