import java.lang.Thread;
import java.util.concurrent.Semaphore;
import java.util.TimerTask;

public class Scheduler {
    private int numOverrunsT0;
    private int numOverrunsT1;
    private int numOverrunsT2;
    private int numOverrunsT3;

    private boolean isFinished;

    Semaphore sem = new Semaphore(0);

    BusyThread bt0 = new BusyThread(1,  2);
    BusyThread bt1 = new BusyThread(2,  3);
    BusyThread bt2 = new BusyThread(4,  4);
    BusyThread bt3 = new BusyThread(16, 5);

    Thread schedulerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            bt0.runThread();
            bt1.runThread();
            bt2.runThread();
            bt3.runThread();
            schedule();
        }
    });

    public Scheduler() {
        schedulerThread.setPriority(Thread.MAX_PRIORITY);
    }

    public void schedule() {
        // RUN FOR 16 PERIODS, 10 TIMES
        for (int i = 0; i < 160; ++i) {
            // WAIT FOR TIMER TO GIVE THE GO AHEAD
            try {
                sem.acquire();
            } catch (InterruptedException e) {}

            // CHECK T0 OVERRUNS
            if (!bt0.isDone()) {
                numOverrunsT0++;
            }

            // SCHEDULE T0
            bt0.sem.release();

            // THREAD T1
            if (i % 2 == 0) {
                // CHECK T1 OVERRUNS
                if (!bt1.isDone()) {
                    numOverrunsT1++;
                }

                // SCHEDULE T1
                bt1.sem.release();
            }

            // THREAD T2
            if (i % 4 == 0) {
                // CHECK T2 OVERRUNS
                if (!bt2.isDone()) {
                    numOverrunsT2++;
                }

                // SCHEDULE T2
                bt2.sem.release();
            }

            // THREAD T3
            if (i % 16 == 0) {
                // CHECK T3 OVERRUNS
                if (!bt3.isDone()) {
                    numOverrunsT3++;
                }

                // SCHEDULE T3
                bt3.sem.release();
            }
        }

        bt0.sem.release();
        bt1.sem.release();
        bt2.sem.release();
        bt3.sem.release();

        bt0.setEveryoneDone(true);
        bt1.setEveryoneDone(true);
        bt2.setEveryoneDone(true);
        bt3.setEveryoneDone(true);

        isFinished = true;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public void joinThreads() {
        bt0.joinThread();
        bt1.joinThread();
        bt2.joinThread();
        bt3.joinThread();
    }

    public void printResults() {
        System.out.println("\n--- RESULTS ---\n");
        System.out.println("T0 COMPLETIONS: " + bt0.numCompletions);
        System.out.println("T0 OVERRUNS:    " + numOverrunsT0);
        System.out.println("\nT1 COMPLETIONS: " + bt1.numCompletions);
        System.out.println("T1 OVERRUNS:    " + numOverrunsT1);
        System.out.println("\nT2 COMPLETIONS: " + bt2.numCompletions);
        System.out.println("T2 OVERRUNS:    " + numOverrunsT2);
        System.out.println("\nT3 COMPLETIONS: " + bt3.numCompletions);
        System.out.println("T3 OVERRUNS:    " + numOverrunsT3 + "\n");
    }
}
