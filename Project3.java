import java.util.Timer;

public class Project3 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        Scheduler RMS = new Scheduler();
        RMSTimer schedTimer = new RMSTimer(RMS);

        RMS.schedulerThread.start();

        timer.scheduleAtFixedRate(schedTimer, 0L, 10L);

        while (!RMS.isFinished()) {
            System.out.print("");
        }

        try {
            RMS.joinThreads();
            RMS.schedulerThread.join();
            timer.cancel();
            RMS.printResults();
        } catch (InterruptedException e) {}
    }
}
