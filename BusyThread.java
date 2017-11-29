import java.util.Random;

public class BusyThread implements Runnable {

    private Random rand = new Random();
    public int priority;
    public int[][] doWorkMatrix = new int[10][10];

    public BusyThread() {
        fillMatrix();
    }

    public BusyThread(int p) {
        this.priority = p;
        fillMatrix();
    }

    public void run() {
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

    public void runWithCount(int runCount) {
        for (int i = 0; i < runCount; ++i) {
            doWork();
        }
    }

    public void printMatrix() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                System.out.print(doWorkMatrix[i][j] + (j == 9 ? "" : ", "));
            }

            System.out.println();
        }
    }

    public void setPriority(int p) {
        this.priority = p;
    }
}
