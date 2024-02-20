public class practice {

    public static void main(String[] args) {

        int N = Integer.parseInt(args[1]);

        for (int i = 0; i < N; i++) {
            String threadName = "I am thread " + i;
            TaskHello task = new TaskHello(threadName, i);
            Thread thread = new Thread(task, threadName);
            thread.start();

        }
    }
}

class TaskHello implements Runnable {
    private String threadName;
    private int threadNumber;

    TaskHello(String threadName, int threadNumber) {
        this.threadName = threadName;
        this.threadNumber = threadNumber;
    }
    

    @Override
    public void run() {
        System.out.println("Hello from thread " + threadName);
    }

}