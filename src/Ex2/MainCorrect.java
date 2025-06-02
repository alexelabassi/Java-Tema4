package Ex2;

import java.util.ArrayList;
import java.util.List;

public class MainCorrect {
    public static void main(String[] args) {
        List<Integer> bufferA = new ArrayList<>();
        List<Integer> bufferB = new ArrayList<>();

        Thread producer1 = new Thread(new ProducerCorrect("Producer1", bufferA, bufferB));
        Thread producer2 = new Thread(new ProducerCorrect("Producer2", bufferA, bufferB));
        Thread consumer1 = new Thread(new ConsumerCorrect("Consumer1", bufferA, bufferB));
        Thread consumer2 = new Thread(new ConsumerCorrect("Consumer2", bufferA, bufferB));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        Logger.log("Threads started.");

    }
}
