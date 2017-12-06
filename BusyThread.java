import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusyThread {
    public Semaphore sem = new Semaphore(0);

    private int period;
    private int numCompletions;
    private Random rand = new Random();
    private boolean finishedWork = true;
    private int[][] doWorkMatrix = new int[10][10];

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 160; ++i) {
                    sem.acquire();
                    finishedWork = false;
                    runWithCount();
                    finishedWork = true;
                }
            } catch(Exception e) {}
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
        } catch (Exception e) {}
    }

    public boolean isDone() {
        return this.finishedWork;
    }

    public int getNumCompletions() {
        return this.numCompletions;
    }

    private void doWork() {
        // CHOOSE A RANDOM INDEX,
        // CHOOSE TWO MORE RANDOM INDEXES,
        // MULTIPLY THEM INTO THE FIRST INDEX
        for (int i = 0; i < 10000; ++i) {
            doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)]
            = doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)]
                * doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)];
        }

        numCompletions++;
    }

    private void runWithCount() {
        for (int i = 0; i < this.period; ++i) {
            doWork();
        }
    }

    private void fillMatrix() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                doWorkMatrix[i][j] = 1;
            }
        }
    }
}
