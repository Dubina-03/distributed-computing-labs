import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class ParallelMultiplication {
    private static ThreadPoolExecutor executor;

    private static class Job implements Runnable {
        private double[][] matA, matB, mat;
        private int i, j;

        public Job(double[][] matA, double[][] matB, double[][] mat, int i, int j) {
            this.matA = matA;
            this.matB = matB;
            this.mat = mat;
            this.i = i;
            this.j = j;
        }

        @Override
        public void run() {
            double sum = 0;
            for(int k = 0; k < matB.length; k++) {
                sum += matA[i][k] * matB[k][j];
            }
            mat[i][j] = sum;
        }
    }

    private static double[][] matMulParallel(double[][] matA, double[][] matB, int num) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(num);
        double[][] mat = new double[matA.length][matB[0].length];
        double sum;
        for(int i = 0; i < matA.length; i++) {
            for(int j = 0; j < matB[0].length; j++) {
                executor.submit(new Job(matA, matB, mat, i, j));
            }
        }

        while(!executor.getQueue().isEmpty());
        executor.shutdown();
        return mat;
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        double[][] m1 = new double[500][500];
        double[][] m2 = new double[500][500];

        for(int i = 0; i < m1.length; i++) {
            for(int j = 0; j < m1[i].length; j++) {
                m1[i][j] = Math.random();
            }
        }

        for(int i = 0; i < m2.length; i++) {
            for(int j = 0; j < m2[i].length; j++) {
                m2[i][j] = Math.random();
            }
        }

        for (int numThreads = 1; numThreads <= 10; numThreads++) {
            long t = System.currentTimeMillis();
            double[][] arr1 = matMulParallel(m1, m2, numThreads);
            /*for(int i = 0; i < arr1.length; i++) {
                for(int j = 0; j < arr1[i].length; j++) {
                    System.out.print(arr1[i][j]);
                }
            }*/
            long n = System.currentTimeMillis();
            out.printf("Parallel Multiplication %d: %d millisecs\n", numThreads, n - t);
        }

        out.close();
    }
}