package src.generator;

public class Generator extends AbstractGenerator {

    /**
     * @inheritDoc
     */
    public double[][] generateMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (Math.random() * 10);
            }
        }
        return matrix;
    }

    /**
     * @inheritDoc
     */
    public void generate() {
        for (int n = 5; n < 55; n += 5) {
            double[][] matrix = generateMatrix(n);
            printMatrix(matrix, 0, n, -1);
        }
    }
}
