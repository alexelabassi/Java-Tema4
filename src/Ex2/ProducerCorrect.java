package Ex2;

import java.util.List;

import static java.lang.Thread.sleep;

public class ProducerCorrect implements Runnable {
    private final String name;
    private final List<Integer> bufferA;
    private final List<Integer> bufferB;

    public ProducerCorrect(String name, List<Integer> bufferA, List<Integer> bufferB) {
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
                    bufferA.add(1);
                    bufferB.add(1);
                    Logger.log(name + " produced");
                }
            }

            try {
                sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
