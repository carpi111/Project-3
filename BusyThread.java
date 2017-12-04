import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusyThread {

    public int runCount;
    public Thread thread;
    public int numCompletions;
    public boolean finishedWork = true;
    public Semaphore sem = new Semaphore(0);
    public int[][] doWorkMatrix = new int[10][10];

    private int priority;
    private Random rand = new Random();


    public BusyThread(int rc, int p) {
        this.runCount = rc;
        this.numCompletions = 0;
        this.priority = Thread.MAX_PRIORITY - p;
        fillMatrix();
    }

    public void createThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sem.acquire();
                    finishedWork = false;

                    runWithCount();

                    finishedWork = true;
                } catch(InterruptedException e) {}
            }
        });

        thread.setPriority(this.priority);
    }

    public void runThread() {
        this.thread.start();
    }

    public void joinThread() {
        try {
            this.thread.join();
        } catch (InterruptedException e) {}
    }

    public void doWork() {
        for (int i = 0; i < 100; ++i) {
            doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)] = doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)]
                * doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)];
        }
    }

    public void runWithCount() {
        for (int i = 0; i < this.runCount; ++i) {
            doWork();
        }

        numCompletions++;
    }

    public boolean isDone() {
        return this.finishedWork;
    }

    private void fillMatrix() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                doWorkMatrix[i][j] = 1;
            }
        }
    }
}
