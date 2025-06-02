package Ex1;

public class Task implements Runnable {
    private final long id;
    private final long duration;
    private volatile boolean finishedNormally = false;

    public Task(long id, long duration) {
        this.id = id;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    @Override
    public void run() {
        synchronized (TaskManager.monitor) {
            System.out.println("Task " + id + " started (duration: " + duration + " ms).");
        }

        try {
            Thread.sleep(duration);
            finishedNormally = true;
        } catch (InterruptedException e) {
            return;
        }

        if (finishedNormally) {
            synchronized (TaskManager.monitor) {
                System.out.println("Task " + id + " completed.");
                TaskManager.statusMap.put(id, Status.COMPLETED);
            }
        }
    }
}
