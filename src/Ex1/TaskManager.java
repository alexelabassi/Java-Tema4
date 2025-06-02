package Ex1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TaskManager {
    public static final Object monitor = new Object();

    public static final ConcurrentHashMap<Long, Status> statusMap = new ConcurrentHashMap<>();

    private final int poolSize;
    private final List<Task> tasks;
    private final long TMAX;

    public TaskManager(int poolSize, long TMAX) {
        this.poolSize = poolSize;
        this.tasks = new ArrayList<>();
        this.TMAX = TMAX;
    }

    public void addTask(Task task) {
        tasks.add(task);
        statusMap.put(task.getId(), Status.PENDING);
    }

    public void runTasks() {
        if (tasks.size() > poolSize) {
            System.out.println("Too many tasks. Maximum allowed: " + poolSize);
            return;
        }

        Thread watchdog = new Thread(new Watchdog(tasks));
        watchdog.start();

        List<Thread> activeThreads = new ArrayList<>();
        int nextIndex = 0;

        while (nextIndex < tasks.size() || !activeThreads.isEmpty()) {
            // scot threadurile terminate
            Iterator<Thread> it = activeThreads.iterator();
            while (it.hasNext()) {
                Thread t = it.next();
                if (!t.isAlive()) {
                    it.remove();
                }
            }

            if (activeThreads.size() < poolSize && nextIndex < tasks.size()) {
                Task task = tasks.get(nextIndex);
                long id = task.getId();

                statusMap.put(id, Status.RUNNING);

                Thread taskThread = new Thread(task);
                taskThread.start();

                new Thread(() -> {
                    try {
                        Thread.sleep(TMAX);
                        if (taskThread.isAlive()) {
                            taskThread.interrupt();
                            statusMap.put(id, Status.TIMED_OUT);
                            synchronized (monitor) {
                                System.out.println("Task " + id + " timed out.");
                            }
                        }
                    } catch (InterruptedException ignored) {
                        // nu-l intrerup
                    }
                }, "TimeoutThread-" + id).start();

                activeThreads.add(taskThread);
                nextIndex++;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }

        watchdog.interrupt();
        try {
            watchdog.join();
        } catch (InterruptedException ignored) {
        }

        System.out.println("All tasks finished. Exiting.");
    }


}
