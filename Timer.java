import java.util.concurrent.Semaphore;

public class Timer {

    public Timer() {}

    public double getTime() {
        return (System.nanoTime() / 1000000);
    }

    public void run(Semaphore s) {
        double startTime = System.nanoTime();

        while ( (System.nanoTime() / 1000000) < (startTime / 1000000 + 10*16*100) ) {
            if ( ((System.nanoTime() - startTime) / 1000000) % 50 == 0) {
                s.release();
            }
        }
    }
}
