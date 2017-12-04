public class Project3 {
    public static void main(String[] args) {

        Timer timer = new Timer();

        Scheduler RMS = new Scheduler();
        RMS.schedulerThread.start();

        timer.run(RMS.sem);

        RMS.joinThreads();
        try {
            RMS.schedulerThread.join();
        } catch (InterruptedException e) {}

        RMS.printResults();
    }
}
