package Ex2;

import java.util.List;

import static java.lang.Thread.sleep;

public class ProducerDeadlock implements Runnable {
    private final String name;
    private final List<Integer> bufferA;
    private final List<Integer> bufferB;
    private final boolean order; // true => A dupa B, false => B dupa A

    public ProducerDeadlock(String name, List<Integer> bufferA, List<Integer> bufferB, boolean order) {
        this.name = name;
        this.bufferA = bufferA;
        this.bufferB = bufferB;
        this.order = order;
    }

    public void run() {
        while (true) {
            if (order) {
                synchronized (bufferA) {
                    Logger.log(name + " locked buffer A");
                    try {
                        sleep(50);
                    } catch (InterruptedException ignored) {
                    }
                    synchronized (bufferB) {
                        Logger.log(name + " locked buffer B");
                        bufferA.add(1);
                        bufferB.add(1);
                        Logger.log(name + " produced (A->B)");
                    }
                }
            } else {
                synchronized (bufferB) {
                    Logger.log(name + " locked buffer B");
                    try {
                        sleep(50);
                    } catch (InterruptedException ignored) {
                    }
                    synchronized (bufferA) {
                        Logger.log(name + " locked buffer A");
                        bufferB.add(1);
                        bufferA.add(1);
                        Logger.log(name + " produced (B->A)");
                    }
                }
            }
            try {
                sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
    }
}



