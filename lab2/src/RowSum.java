import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class RowSum {
    private static ThreadPoolExecutor executor;

    private static class Job implements Runnable {
        private double[] row;
        private int i, processn;

        public Job(double[] row, int i, int processn) {
            this.row = row;
            this.i = i;
            this.processn = processn;
        }

        @Override
        public void run() {
            double sum = 0;
            for (double v : row) {
                sum += v;
            }
            System.out.printf("\nProcess %d Row %d sum: %f", processn, i, sum);
        }
    }

    private static void rowCount(double[][] matA, int num) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(num);
        for(int i = 0; i < matA.length; i++) {
            executor.submit(new Job(matA[i], i, num));
        }

        while(!executor.getQueue().isEmpty());
        executor.shutdown();
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        double[][] m1 = new double[1000][1200];

        for(int i = 0; i < m1.length; i++) {
            for(int j = 0; j < m1[i].length; j++) {
                m1[i][j] = Math.random();
            }
        }

        for (int numThreads = 1; numThreads <= 10; numThreads++) {
            long t = System.currentTimeMillis();
            rowCount(m1, numThreads);
            long n = System.currentTimeMillis();
            out.printf("\n\nParallel Counting %d: %d millisecs\n", numThreads, n - t);
        }

        out.close();
    }
}