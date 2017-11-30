import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusyThread {

    public boolean finishedWork = false;
    public int runCount;
    public int[][] doWorkMatrix = new int[10][10];

    private Random rand = new Random();
    private Semaphore sem = new Semaphore(0);

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                sem.acquire();
            } catch(InterruptedException e) {}

            finishedWork = false;

            // DO WORK
            for (int i = 0; i < runCount; ++i) {
                for (int j = 0; j < 100; ++j) {
                    doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)] = doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)]
                        * doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)];
                }
            }

            finishedWork = true;
        }
    });

    public BusyThread() {
        fillMatrix();
    }

    public BusyThread(int rc) {
        this.runCount = rc;
        fillMatrix();
    }

    private void fillMatrix() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                doWorkMatrix[i][j] = 1;
            }
        }
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
    }
}
