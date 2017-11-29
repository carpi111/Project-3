import java.util.concurrent.Semaphore;

public class Project3 {
    public static void main(String[] args) {
        BusyThread p1 = new BusyThread(4);
        BusyThread p2 = new BusyThread(3);
        BusyThread p3 = new BusyThread(2);
        BusyThread p4 = new BusyThread(1);

        Semaphore s1  = new Semaphore(0);
        Semaphore s2  = new Semaphore(0);
        Semaphore s4  = new Semaphore(0);
        Semaphore s16 = new Semaphore(0);

        // THREAD T0
        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                // T0 WORK
                try {
                    s1.acquire();
                } catch(InterruptedException e) {}

                p1.runWithCount(1);
            }
        });

        // THREAD T1
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // T1 WORK
                try {
                    s2.acquire();
                } catch(InterruptedException e) {}

                p2.runWithCount(2);
            }
        });

        // THREAD T2
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // T2 WORK
                try {
                    s4.acquire();
                } catch(InterruptedException e) {}

                p3.runWithCount(4);
            }
        });

        // THREAD T3
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                // T3 WORK
                try {
                    s16.acquire();
                } catch(InterruptedException e) {}

                p4.runWithCount(16);
            }
        });
    }
}
