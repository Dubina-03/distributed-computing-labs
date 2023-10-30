package classes;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntegralCalculation {
    private static double sums;

    private class Job implements Runnable {
        double x, h;
        Function f;

        public Job(double h, double x, Function f) {
            this.x = x;
            this.h = h;
            this.f = f;
        }

        @Override
        public void run() {
            double res = 0;
            try {
                res = f.calculate(x);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                synchronized (IntegralCalculation.this) {
                    sums += res * h;
                }
            }
        }
    }

    public double calculation (double a, double b, int n, Function f, int numThread){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThread);
        DecimalFormat df = new DecimalFormat("#.#############");
        sums = 0;
        double s = 0;

        double h = (b - a) / n;

        for (int i = 1; i < n + 1; i++) {
            double x = a + i * h;
            executor.submit(new Job(h, x, f));
            s += f.calculate(x)*h;
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Double.parseDouble(df.format(s)));

        return sums;
    }
}
