import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


public class AllRowSum {
    private static ThreadPoolExecutor executor;

    private static double allRowCount(double[][] matA, int num) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(num);
        double sum = 0.0;
        Future<Double>[] sumsFutures = new Future[matA.length];

        for (int i = 0; i < matA.length; i++) {
            double[] row = matA[i];
            sumsFutures[i] = executor.submit(new Callable<Double>() {
                public Double call() throws Exception {
                    return Arrays.stream(row).reduce(0, Double::sum);
                }
            });
        }

        for (Future<Double> future : sumsFutures) {
            try {
                sum += future.get(); // Get the computed sum from the future
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return sum;

    }

    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        double[][] m1 = new double[1000][1000];

        for(int i = 0; i < m1.length; i++) {
            for(int j = 0; j < m1[i].length; j++) {
                m1[i][j] = Math.random();
            }
        }

        double s =0;
        for(int i = 0; i < m1.length; i++) {
            for(int j = 0; j < m1[i].length; j++) {
                s  += m1[i][j];
            }
        }
        out.printf("\n\nReal sum %f\n", s);

        for (int numThreads = 1; numThreads <= 10; numThreads++) {
            long t = System.currentTimeMillis();
            double sum = allRowCount(m1, numThreads);
            long n = System.currentTimeMillis();
            out.printf("\n\nParallel Counting %d sum %f: %d millisecs\n", numThreads, sum, n - t);
        }

        out.close();
    }
}