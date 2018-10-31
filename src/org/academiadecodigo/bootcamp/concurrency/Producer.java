package org.academiadecodigo.bootcamp.concurrency;

import org.academiadecodigo.bootcamp.concurrency.bqueue.BQueue;

/**
 * Produces and stores integers into a blocking queue
 */
public class Producer implements Runnable {

    private final BQueue<Integer> queue;
    private int elementsToProduce;

    /**
     * @param queue      the blocking queue to add elements to
     * @param elementsToProduce the number of elements to produce
     */
    public Producer(BQueue queue, int elementsToProduce) {
        this.queue = queue;
        this.elementsToProduce = elementsToProduce;
    }

    @Override
    public void run() {
        while (elementsToProduce > 0) {
            produce();
        }
    }

    private void produce() {
        synchronized (queue) {
            int producedElem = (int) (Math.random() * 10) + 1;
            queue.offer(producedElem);
            System.out.println(Thread.currentThread().getName() + " adding number " + producedElem);
            if (queue.getSize() == queue.getLimit()) {
                System.out.println(Thread.currentThread().getName() + " left the queue full");
            }
        }
        elementsToProduce--;
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
