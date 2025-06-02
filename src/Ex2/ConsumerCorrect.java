package Ex2;

import java.util.List;

import static java.lang.Thread.sleep;

public class ConsumerCorrect implements Runnable {
    private final String name;
    private final List<Integer> bufferA;
    private final List<Integer> bufferB;

    public ConsumerCorrect(String name, List<Integer> bufferA, List<Integer> bufferB) {
        this.name = name;
        this.bufferA = bufferA;
        this.bufferB = bufferB;
    }

    public void run() {
        while (true) {
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

            try {
                sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
