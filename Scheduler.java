import java.lang.Thread;
import java.util.concurrent.Semaphore;

public class Scheduler {

    public int numOverrunsT0;
    public int numOverrunsT1;
    public int numOverrunsT2;
    public int numOverrunsT3;

    public int timeUnit;

    Semaphore sem = new Semaphore(1);
    BusyThread t0;
    BusyThread t1;
    BusyThread t2;
    BusyThread t3;

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
        }
    });

    public Scheduler() {
        t0 = new BusyThread(1,  2);
        t1 = new BusyThread(2,  3);
        t2 = new BusyThread(4,  4);
        t3 = new BusyThread(16, 5);

        timeUnit = -1;

        t0.thread.start();
        t1.thread.start();
        t2.thread.start();
        t3.thread.start();
    }

    public void schedule() {

        for (int i = 0; i < 16; ++i) {
            timeUnit++;

            try {
                sem.acquire();
            } catch (InterruptedException e) {}

            if (!t0.isDone()) {
                System.out.println("T0 OVERRUN");
                numOverrunsT0++;
            }

            t0.sem.release();

            if (timeUnit % 2 == 0) {
                if (!t1.isDone()) {
                    System.out.println("T1 OVERRUN");
                    numOverrunsT1++;
                }
                t1.sem.release();
            }

            if (timeUnit % 4 == 0) {
                if (!t2.isDone()) {
                    System.out.println("T2 OVERRUN");
                    numOverrunsT2++;
                }
                t2.sem.release();
            }

            if (timeUnit % 16 == 0) {
                if (!t3.isDone()) {
                    System.out.println("T3 OVERRUN");
                    numOverrunsT3++;
                }
                t3.sem.release();
            }
        }
    }

    public void joinThreads() {
        try {
            t0.thread.join();
            t1.thread.join();
            t2.thread.join();
            t3.thread.join();
        } catch (InterruptedException e) {}
    }
}
