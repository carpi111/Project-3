import java.util.concurrent.Semaphore;

public class Scheduler {
    private int numOverrunsT0;
    private int numOverrunsT1;
    private int numOverrunsT2;
    private int numOverrunsT3;

    Semaphore sem = new Semaphore(0);
    Semaphore mainSem = new Semaphore(0);

    BusyThread bt0 = new BusyThread(1,  2);
    BusyThread bt1 = new BusyThread(2,  4);
    BusyThread bt2 = new BusyThread(4,  6);
    BusyThread bt3 = new BusyThread(16, 8);

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
            // WAIT FOR TIMER TO RELEASE SEMAPHORE
            try {
                sem.acquire();
            } catch (InterruptedException e) {}

            // CHECK T0 OVERRUNS
            if (!bt0.isDone())
                numOverrunsT0++;

            // SCHEDULE T0
            bt0.sem.release();

            // THREAD T1
            if (i % 2 == 0) {
                // CHECK T1 OVERRUNS
                if (!bt1.isDone())
                    numOverrunsT1++;

                // SCHEDULE T1
                bt1.sem.release();
            }

            // THREAD T2
            if (i % 4 == 0) {
                // CHECK T2 OVERRUNS
                if (!bt2.isDone())
                    numOverrunsT2++;

                // SCHEDULE T2
                bt2.sem.release();
            }

            // THREAD T3
            if (i % 16 == 0) {
                // CHECK T3 OVERRUNS
                if (!bt3.isDone())
                    numOverrunsT3++;

                // SCHEDULE T3
                bt3.sem.release();
            }
        }

        // STOP EVERYONE FROM WAITING FOR THEIR SEMAPHORE
        bt0.thread.interrupt();
        bt1.thread.interrupt();
        bt2.thread.interrupt();
        bt3.thread.interrupt();

        // FLAG MAIN TO PRINT RESULTS
        mainSem.release();
    }

    public void joinThreads() {
        bt0.joinThread();
        bt1.joinThread();
        bt2.joinThread();
        bt3.joinThread();
        joinSchedulerThread();
    }

    public void printResults() {
        System.out.println("\n--- RESULTS ---\n");
        System.out.println("T0 COMPLETIONS: " + bt0.getNumCompletions());
        System.out.println("T0 OVERRUNS:    " + numOverrunsT0);
        System.out.println("\nT1 COMPLETIONS: " + bt1.getNumCompletions());
        System.out.println("T1 OVERRUNS:    " + numOverrunsT1);
        System.out.println("\nT2 COMPLETIONS: " + bt2.getNumCompletions());
        System.out.println("T2 OVERRUNS:    " + numOverrunsT2);
        System.out.println("\nT3 COMPLETIONS: " + bt3.getNumCompletions());
        System.out.println("T3 OVERRUNS:    " + numOverrunsT3 + "\n");
    }

    private void joinSchedulerThread() {
        try {
            this.schedulerThread.join();
        } catch (Exception e) {}
    }
}
