import java.lang.Thread;
import java.util.concurrent.Semaphore;

public class Scheduler {

    private int numOverrunsBt0;
    private int numOverrunsBt1;
    private int numOverrunsBt2;
    private int numOverrunsBt3;

    Semaphore sem = new Semaphore(0);

    BusyThread bt0;
    BusyThread bt1;
    BusyThread bt2;
    BusyThread bt3;

    Thread schedulerThread;


    public Scheduler() {
        bt0 = new BusyThread(1,  2);
        bt1 = new BusyThread(2,  3);
        bt2 = new BusyThread(4,  4);
        bt3 = new BusyThread(16, 5);

        schedulerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    schedule();
                    break;
                }
            }
        });

        schedulerThread.setPriority(Thread.MAX_PRIORITY);

        bt0.thread.start();
        bt1.thread.start();
        bt2.thread.start();
        bt3.thread.start();
    }

    public void schedule() {
        for (int i = 0; i < 160; ++i) {
            try {
                sem.acquire();
            } catch (InterruptedException e) {}

            if (!bt0.isDone()) {
                //System.out.println("bt0 OVERRUN");
                numOverrunsBt0++;
            }

            // SCHEDULE T0
            //bt0.createThread();
            //bt0.thread.start();
            bt0.sem.release();

            if (i % 2 == 0) {
                if (!bt1.isDone()) {
                    //System.out.println("bt1 OVERRUN");
                    numOverrunsBt1++;
                }

                // SCHEDULE T1
                //bt1.createThread();
                //bt1.thread.start();
                bt1.sem.release();
            }

            if (i % 4 == 0) {
                if (!bt2.isDone()) {
                    //System.out.println("bt2 OVERRUN");
                    numOverrunsBt2++;
                }

                // SCHEDULE T2
                //bt2.createThread();
                //bt2.thread.start();
                bt2.sem.release();
            }

            if (i % 16 == 0) {
                if (!bt3.isDone()) {
                    //System.out.println("bt3 OVERRUN");
                    numOverrunsBt3++;
                }

                // SCHEDULE T3
                //bt3.createThread();
                //bt3.thread.start();
                bt3.sem.release();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }
    }

    public void joinThreads() {
        try {
            bt0.thread.join();
            bt1.thread.join();
            bt2.thread.join();
            bt3.thread.join();
        } catch (InterruptedException e) {
            System.out.println("*** FAILED TO JOIN THREADS ***");
        }
    }

    public void printResults() {
        System.out.println("--- RESULTS ---");
        System.out.println("T0 COMPLETIONS: " + bt0.numCompletions);
        System.out.println("T0 OVERRUNS:    " + numOverrunsBt0);
        System.out.println("\nT1 COMPLETIONS: " + bt1.numCompletions);
        System.out.println("T1 OVERRUNS:    " + numOverrunsBt1);
        System.out.println("\nT2 COMPLETIONS: " + bt2.numCompletions);
        System.out.println("T2 OVERRUNS:    " + numOverrunsBt2);
        System.out.println("\nT3 COMPLETIONS: " + bt3.numCompletions);
        System.out.println("T3 OVERRUNS:    " + numOverrunsBt3);
    }
}
