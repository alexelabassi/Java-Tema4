package Ex2;

import java.util.List;

import static java.lang.Thread.sleep;

public class ConsumerDeadlock implements Runnable {
    private final String name;
    private final List<Integer> bufferA;
    private final List<Integer> bufferB;
    private final boolean order; // true => A dupa B, false => B dupa A

    public ConsumerDeadlock(String name, List<Integer> bufferA, List<Integer> bufferB, boolean order) {
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
                        if (!bufferA.isEmpty()) {
                            bufferA.remove(0);
                            Logger.log(name + " consumed from A");
                        }
                        if (!bufferB.isEmpty()) {
                            bufferB.remove(0);
                            Logger.log(name + " consumed from B");
                        }
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
                        if (!bufferB.isEmpty()) {
                            bufferB.remove(0);
                            Logger.log(name + " consumed from B");
                        }
                        if (!bufferA.isEmpty()) {
                            bufferA.remove(0);
                            Logger.log(name + " consumed from A");
                        }
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
