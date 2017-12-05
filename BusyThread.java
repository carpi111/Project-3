import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusyThread {
    public int period;
    public int numCompletions;
    public boolean everyoneDone;
    public boolean finishedWork = true;
    public Semaphore sem = new Semaphore(0);

    private Random rand = new Random();
    private int[][] doWorkMatrix = new int[10][10];

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (!everyoneIsDone()) {
                    sem.acquire();
                    // mutex.lock
                    finishedWork = false;
                    //mutex.unlock
                    runWithCount();
                    finishedWork = true;
                }
            } catch(InterruptedException e) {}
        }
    });

    public BusyThread(int per, int pri) {
        fillMatrix();
        this.period = per;
        thread.setPriority(Thread.MAX_PRIORITY - pri);
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
        for (int i = 0; i < 10000; ++i) {
            doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)] = doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)]
                * doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)];
        }

        numCompletions++;
    }

    public void runWithCount() {
        for (int i = 0; i < this.period; ++i) {
            doWork();
        }
    }

    public boolean isDone() {
        return this.finishedWork;
    }

    public void setEveryoneDone(boolean val) {
        this.everyoneDone = val;
    }

    private boolean everyoneIsDone() {
        return this.everyoneDone;
    }

    private void fillMatrix() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                doWorkMatrix[i][j] = 1;
            }
        }
    }
}
