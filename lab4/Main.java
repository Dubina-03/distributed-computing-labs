public class Main {
    public static void main (String[] args) {
        int N = 4;
        double[] B = new double[N];
        double[][] A = new double[N][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = Math.random();

        for (int i = 0; i < N; i++)
            B[i] = Math.random();
        for (int iter = 1; iter < 7; iter++) {
            System.out.printf("\n\nNumber of threads: %d", iter);
            GaussianElimination ge = new GaussianElimination(A, B, iter);
            ge.solve();
        }
    }
}
