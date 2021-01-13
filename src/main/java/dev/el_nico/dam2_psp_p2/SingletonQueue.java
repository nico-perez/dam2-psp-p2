package dev.el_nico.dam2_psp_p2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class SingletonQueue {

    /** SingletonQueue instance */
    private static SingletonQueue SQ = init();

    private final Lock LOCK;
    private final Condition NOT_EMPTY;

    private Queue<Object> Q = new ConcurrentLinkedQueue<>();

    private SingletonQueue() {
        LOCK = new ReentrantLock();
        NOT_EMPTY = LOCK.newCondition();
        Q = new ConcurrentLinkedQueue<>();
    }

    private static synchronized SingletonQueue init() {
        return SQ == null ? new SingletonQueue() : SQ;
    }

    public static void enqueue(Object o) {
        SQ.LOCK.lock();
        SQ.Q.add(o);
        if (SQ.Q.size() == 1) {
            SQ.NOT_EMPTY.signal();
        }
        SQ.LOCK.unlock();
    }

    public static Object dequeue() {
        Object o;
        if ((o = SQ.Q.poll()) == null) {
            SQ.LOCK.lock();
            try {
                SQ.NOT_EMPTY.await();
                o = SQ.Q.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                SQ.LOCK.unlock();
            }
        }
        return o;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("no!");
    }
}
