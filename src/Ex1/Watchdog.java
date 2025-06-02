package Ex1;

import java.util.Date;
import java.util.List;

public class Watchdog implements Runnable {
    private final List<Task> allTasks;

    public Watchdog(List<Task> allTasks) {
        this.allTasks = allTasks;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (TaskManager.monitor) {
                System.out.println("\n-----------------------");
                System.out.println("WATCHDOG: ");
                for (Task t : allTasks) {
                    long id = t.getId();
                    Status s = TaskManager.statusMap.get(id);
                    System.out.println("Task " + id + " -> " + s);
                }
                System.out.println("-----------------------");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
