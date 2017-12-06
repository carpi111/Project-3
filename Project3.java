import java.util.Timer;

public class Project3 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        Scheduler RMS = new Scheduler();
        RMSTimer schedTimer = new RMSTimer(RMS);

        RMS.schedulerThread.start();

        // CALL schedTimer 'run()' EVERY 10ms WITH NO DELAY
        timer.scheduleAtFixedRate(schedTimer, 0L, 10L);

        // WAIT UNTIL EVERYONE IS DONE TO JOIN AND PRINT RESULTS
        while (!RMS.isFinished()) {
            // COULDN'T LEAVE THE LOOP EMPTY WITHOUT IT GETTING STUCK HERE
            try {
                Thread.sleep(10);
            } catch (Exception e) {}
        }

        // CLEAN UP AND PRINT RESULTS
        try {
            RMS.joinThreads();
            timer.cancel();
            RMS.printResults();
        } catch (Exception e) {}
    }
}
