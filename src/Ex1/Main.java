package Ex1;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager(5, 2500);

        manager.addTask(new Task(1, 1000));
        manager.addTask(new Task(2, 3000));
        manager.addTask(new Task(3, 2000));
        manager.addTask(new Task(4, 4000));
        manager.addTask(new Task(5, 1500));

        manager.runTasks();
    }
}
