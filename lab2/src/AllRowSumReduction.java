import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Math.round;


public class AllRowSumReduction {
    private static ThreadPoolExecutor executor;
    private static double sums;

    private static class Job implements Runnable {
        private double[][] submatrix;

        public Job(double[][] submatrix) {
            this.submatrix = submatrix;
        }

        @Override
        public void run() {
            double s = 0;
            try {
                for (int i = 0; i < submatrix.length; i++) {
                    s += Arrays.stream(submatrix[i]).reduce(0.0, Double::sum);
                }
            } catch (Exception e) {
                // Handle any exceptions that may occur during execution
                e.printStackTrace();
            } finally {
                // Ensure that sums is updated even if an exception occurs
                sums += s;
            }
        }
    }

    private static void allRowCount(double[][] matA, int num) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(num);
        sums = 0;

        int rowsPerPart = matA.length / num;
        int extraRows = matA.length % num; // Calculate the remaining rows

        for (int part = 0; part < num; part++) {
            int startRow = part * rowsPerPart;
            int endRow = (part + 1) * rowsPerPart;
            //System.out.printf("\n%d, %d, threads %d", startRow, endRow, num);

            if (part == num - 1) {
                endRow += extraRows;
            }
            executor.submit(new Job(Arrays.copyOfRange(matA, startRow, endRow)));
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            allRowCount(m1, numThreads);
            long n = System.currentTimeMillis();
            out.printf("\n\nParallel Counting %d sum %f: %d millisecs\n", numThreads, sums, n - t);
        }

        out.close();
    }
}