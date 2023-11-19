import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PiCalculation2 {
    private static int circle_points = 0, square_points = 0;
    private static double pi;
    private final Random generator = new Random(5);

    private class Job implements Runnable {
        private static int i;
        public Job(int i) {this.i=i;
        }

        @Override
        public void run() {
            double rand_x = generator.nextDouble()*2-1;
            double rand_y = generator.nextDouble()*2-1;

            double origin_dist = rand_x * rand_x + rand_y * rand_y;

            synchronized (PiCalculation2.this) {
                if (origin_dist <= 1)
                    circle_points++;
                square_points++;
                pi = ((4.0 * circle_points) / square_points);
            }
        }
    }
    private void piCalculation(int j) {
        System.out.printf("\nThe number of threads is %d\n", j);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(j);
        for(int i = 0; i < 1000000; i++) {
            executor.submit(new Job(i));
            if (i+1 == 1000000)
                System.out.println(pi);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int j = 1; j <= 10; j++) {
            PiCalculation2 pi = new PiCalculation2();
            pi.piCalculation(j);
        }
    }
}
