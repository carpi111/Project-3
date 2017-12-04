import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusyThread {

    public int runCount;
    public int numCompletions;
    public boolean finishedWork = true;
    public int[][] doWorkMatrix = new int[10][10];

    private Random rand = new Random();
    public Semaphore sem = new Semaphore(0);

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            //try {
                //sem.acquire();
                finishedWork = false;

                // DO WORK
                runWithCount();
                //for (int i = 0; i < runCount; ++i) {
                    //for (int j = 0; j < 100; ++j) {
                        //doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)] = doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)]
                            //* doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)];
                    //}
                //}

                finishedWork = true;
            //} catch(InterruptedException e) {}
        }
    });

    public BusyThread() {
        fillMatrix();
    }

    public BusyThread(int rc, int p) {
        this.runCount = rc;
        this.thread.setPriority(Thread.MAX_PRIORITY - p);
        fillMatrix();
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
        try {
            sem.acquire();
        } catch (InterruptedException e) {}

        for (int i = 0; i < 100; ++i) {
            doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)] = doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)]
                * doWorkMatrix[rand.nextInt(10)][rand.nextInt(10)];
        }

        numCompletions++;
    }

    public void runWithCount() {
        for (int i = 0; i < this.runCount; ++i) {
            doWork();
        }
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
