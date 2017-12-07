import java.util.Timer;
import java.util.concurrent.Semaphore;

public class Project3 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        Scheduler RMS = new Scheduler();
        Semaphore mainSem = new Semaphore(0);
        RMSTimer schedTimer = new RMSTimer(RMS);

        RMS.schedulerThread.start();

        // CALL schedTimer 'run()' EVERY 10ms WITH NO DELAY
        timer.scheduleAtFixedRate(schedTimer, 0L, 10L);

        // WAIT UNTIL EVERYONE IS DONE TO JOIN AND PRINT RESULTS
        try {
            RMS.mainSem.acquire();
        } catch (InterruptedException e) {}

        // CLEAN UP AND PRINT RESULTS
        try {
            RMS.joinThreads();
            timer.cancel();
            RMS.printResults();
        } catch (Exception e) {}
    }
}
