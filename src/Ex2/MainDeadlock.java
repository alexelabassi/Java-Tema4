package Ex2;

import java.util.ArrayList;
import java.util.List;

public class MainDeadlock {

    public static void main(String[] args) {
        List<Integer> bufferA = new ArrayList<>();
        List<Integer> bufferB = new ArrayList<>();

        Thread producer1 = new Thread(new ProducerDeadlock("Producer1", bufferA, bufferB, true));
        Thread producer2 = new Thread(new ProducerDeadlock("Producer2", bufferA, bufferB, false));
        Thread consumer1 = new Thread(new ConsumerDeadlock("Consumer1", bufferA, bufferB, true));
        Thread consumer2 = new Thread(new ConsumerDeadlock("Consumer2", bufferA, bufferB, false));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        Logger.log("Threads started. They will run indefinitely, causing a deadlock.");

    }
}