package dev.el_nico.dam2_psp_p2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCircularBuffer<T> {

    private final Lock LOCK;
    private final Condition NOT_EMPTY;
    private final Condition NOT_FULL;

    private final int MAX_SIZE;

    private boolean isFull = false;
    private int firstOccupied = 0;
    private int lastFree = 0;
    private Object[] q;

    public ConcurrentCircularBuffer(int maxSize) {
        LOCK = new ReentrantLock();
        NOT_EMPTY = LOCK.newCondition();
        NOT_FULL = LOCK.newCondition();

        MAX_SIZE = maxSize;
        q = new Object[MAX_SIZE];
    }

    public int size() {
        if (firstOccupied == lastFree) {
            return 0;
        } else if (firstOccupied < lastFree) {
            return lastFree - firstOccupied;
        } else {
            return MAX_SIZE - firstOccupied + lastFree;
        }
    }

    public boolean isEmpty() {
        return firstOccupied == lastFree && !isFull;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {

        T elem;

        LOCK.lock();

        while (isEmpty()) {
            try {
                NOT_EMPTY.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                LOCK.unlock();
                return null;
            }
        }

        // increment & loop-around
        elem = (T) q[firstOccupied++];
        if (firstOccupied == MAX_SIZE) {
            firstOccupied = 0;
        }

        // update full & signal
        isFull = false;
        if ((lastFree + 1) % MAX_SIZE == firstOccupied) {
            NOT_FULL.signalAll();
        }

        LOCK.unlock();

        return elem;
    }

    public void enqueue(T elem) {
        LOCK.lock();

        // await until not full
        while (isFull) {
            try {
                NOT_FULL.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        }

        // increment & loop-around
        q[lastFree++] = elem;
        if (lastFree == MAX_SIZE) {
            lastFree = 0;
        }

        // update full & signal
        isFull = lastFree == firstOccupied;
        if ((firstOccupied + 1) % MAX_SIZE == lastFree) {
            NOT_EMPTY.signalAll();
        }
 
        LOCK.unlock();
    }
}
