import java.lang.Thread;
import java.util.concurrent.Semaphore;

public class Scheduler {

    Semaphore sem = new Semaphore(0);

    BusyThread worker1 = new BusyThread(1);
    BusyThread worker2 = new BusyThread(2);
    BusyThread worker3 = new BusyThread(4);
    BusyThread worker4 = new BusyThread(16);

    public Scheduler() {
        //thread.setPriority(Thread.MAX_PRIORITY);
        //t1.setPriority(Thread.MAX_PRIORITY - 1);
        //t2.setPriority(Thread.MAX_PRIORITY - 2);
        //t3.setPriority(Thread.MAX_PRIORITY - 3);
    }
}
