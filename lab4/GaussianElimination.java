import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** Class GaussianElimination **/
public class GaussianElimination {
    private static double[] solution;
    private static double[][] A;
    private static double[] B;
    private static int iter;

    private class Job_forward implements Runnable {
        int k, N;

        public Job_forward(int k, int N) {
            this.k = k;
            this.N = N;
        }

        @Override
        public void run() {
            int max = k;
            synchronized (GaussianElimination.this) {
                for (int i = k + 1; i < N; i++)
                    if (Math.abs(A[i][k]) > Math.abs(A[max][k]))
                        max = i;
                /** swap row in A and B matrix **/
                double[] temp = A[k];
                A[k] = A[max];
                A[max] = temp;

                double t = B[k];
                B[k] = B[max];
                B[max] = t;

                /** pivot within A and B **/
                for (int i = k + 1; i < N; i++)
                {
                    double factor = A[i][k] / A[k][k];
                    B[i] -= factor * B[k];
                    for (int j = k; j < N; j++)
                        A[i][j] -= factor * A[k][j];
                }
            }
        }
    }

    public GaussianElimination(double[][] A, double[] B, int iter) {
        GaussianElimination.A = A;
        GaussianElimination.B = B;
        GaussianElimination.iter = iter;
    }

    public void solve(){
        if (iter== 1) {
            System.out.println("\nThe matrix : ");
            printRowEchelonForm(A, B);
        }
        long t = System.currentTimeMillis();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(iter);
        int N = B.length;
        for (int k = 0; k < N; k++)
        {
            executor.submit(new Job_forward(k, N));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** Print row echelon form **/
        System.out.println("\nThe echelon matrix form: ");
        printRowEchelonForm(A, B);

        /** back substitution **/
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(iter);
        solution = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A[i][j] * solution[j];
            }
            solution[i] = (B[i] - sum) / A[i][i];
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long n = System.currentTimeMillis();
        /** Print solution **/
        printSolution(solution);
        System.out.printf("Execution time: %d", n - t);
    }
    /** function to print in row    echleon form **/
    public static void printRowEchelonForm(double[][] A, double[] B)
    {
        int N = B.length;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.printf("%.3f ", A[i][j]);
            System.out.printf("| %.3f\n", B[i]);
        }
        System.out.println();
    }
    /** function to print solution **/
    public void printSolution(double[] sol)
    {
        int N = sol.length;
        System.out.println("Solution : ");
        for (int i = 0; i < N; i++)
            System.out.printf("%.3f ", sol[i]);
        System.out.println();
    }
}