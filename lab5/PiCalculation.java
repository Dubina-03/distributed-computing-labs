import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PiCalculation {
    private static double sum, w;

    private class Job implements Runnable {
        private int i;

        public Job(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            double x = w * (i - 0.5);
            synchronized (PiCalculation.this) {
                sum += 4 / (1 + x * x);
            }
        }
    }
    private void piCalculation(int j) {
        sum = 0;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(j);
        for(int i = 0; i < 1000000; i++) {
            executor.submit(new Job(i));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("\nThe number of threads is %d the pi = %f", j, w * sum);
    }

    public static void main(String[] args) {
        w =  1.0 / 1000000.0;
        for(int j = 1; j <= 10; j++) {
            PiCalculation pi = new PiCalculation();
            pi.piCalculation(j);
        }
    }
}
