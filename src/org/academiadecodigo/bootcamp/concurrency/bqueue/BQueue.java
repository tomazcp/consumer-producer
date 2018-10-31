package org.academiadecodigo.bootcamp.concurrency.bqueue;

import java.util.LinkedList;

/**
 * Blocking Queue
 *
 * @param <T> the type of elements stored by this list
 */
public class BQueue<T> {

    final private LinkedList<T> list;
    final private int limit;

    /**
     * Constructs a new list with a maximum size
     *
     * @param limit the list size
     */
    public BQueue(int limit) {
        this.limit = limit;
        list = new LinkedList<>();
    }

    /**
     * Inserts the specified element into the list
     * Blocking operation if the list is full
     *
     * @param data the data to add to the list
     */
    public void offer(T data) {
        synchronized (this) {
            while (list.size() == limit) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            list.offer(data);
            System.out.println("## ELEMENT ADDED, SIZE OF QUEUE IS NOW " + list.size());
            notifyAll();
        }
    }

    /**
     * Retrieves and removes data from the head of the list
     * Blocking operation if the list is empty
     *
     * @return the data from the head of the list
     */
    public T poll() {
        synchronized (this) {
            while (list.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            T value = list.removeFirst();
            System.out.println("## ELEMENT REMOVED, SIZE OF QUEUE IS NOW " + list.size());

            notifyAll();
            return value;
        }
    }

    /**
     * Gets the number of elements in the list
     *
     * @return the number of elements
     */
    public int getSize() {
        synchronized (this) {
            return list.size();
        }
    }

    /**
     * Gets the maximum number of elements that can be present in the list
     *
     * @return the maximum number of elements
     */
    public int getLimit() {
        return limit;
    }

}
