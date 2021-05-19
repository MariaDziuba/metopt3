package src.generator;

public class Generator52 extends AbstractGenerator {
    public double[][] generateMatrix(int n) {
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
            matrix[i][i] = -sum;
            if (i == 0) {
                matrix[i][i] += 1;
            }
        }
        return matrix;
    }

    public void generate() {
        for (int n = 15; n < 1000; n += 50) {
            double[][] matrix = generateMatrix(n);
            printMatrix(matrix, 52, n, -1);
        }
    }
}