package src;

public class Generator2 extends AbstractGenerator {

    public double[][] generateMatrix(int n, int k) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int el = (int) (Math.random() * 5) * -1;
                    sum += el;
                    matrix[i][j] = el;
                }
            }
            if (i == 0) {
                matrix[i][i] = -sum;
                double pow = 1;
                for (int p = 0; p < k; p++) {
                    pow *= 0.1;
                }
                matrix[i][i] -= pow * (n - 1);
            } else {
                matrix[i][i] = -sum;
            }
        }
        return matrix;
    }

    public void generate() {
        for (int n = 15; n < 1000; n += 50) {
            for (int k = 0; k < 7; k++) {
                double[][] matrix = generateMatrix(n, k);
                printMatrix(matrix, n, k);
            }
        }
    }
}