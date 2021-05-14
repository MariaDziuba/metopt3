package src.generator;

public class Generator3 extends AbstractGenerator {

    public double[][] generateMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double zn = i + j + 1;
                matrix[i][j] = 1 / zn;
            }
        }
        return matrix;
    }

    public void generate() {
        for (int n = 15; n < 1000; n += 50) {
            double[][] matrix = generateMatrix(n);
            printMatrix(matrix, false, n, -1);
        }
    }
}
