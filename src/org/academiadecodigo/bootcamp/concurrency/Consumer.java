package org.academiadecodigo.bootcamp.concurrency;

import org.academiadecodigo.bootcamp.concurrency.bqueue.BQueue;

/**
 * Consumer of integers from a blocking queue
 */
public class Consumer implements Runnable {

    private final BQueue<Integer> queue;
    private int consumedElements;

    /**
     * @param queue      the blocking queue to consume elements from
     * @param consumedElements the number of elements to consume
     */
    public Consumer(BQueue queue, int consumedElements) {
        this.queue = queue;
        this.consumedElements = consumedElements;
    }

    @Override
    public void run() {
        while (consumedElements > 0) {
            consume();
        }
    }

    private void consume() {
        synchronized (queue) {
            int elem = queue.poll();
            System.out.println(Thread.currentThread().getName() +
                    " removing number " + elem);

            if (queue.getSize() == 0) {
                System.out.println(Thread.currentThread().getName() + " left the queue empty");
            }
        }

        consumedElements--;

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            System.out.println();
        }
    }
}
