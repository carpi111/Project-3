import java.util.TimerTask;

public class RMSTimer extends TimerTask {
    private Scheduler scheduler;

    public RMSTimer(Scheduler sched) {
        this.scheduler = sched;
    }

    // GETS CALLED BY 'scheduleAtFixedRate()' IN MAIN
    @Override
    public void run() {
        scheduler.sem.release();
    }
}
