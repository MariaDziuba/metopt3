package src.generator;

/**
 * Генератор для задания 3
 */
public class Generator3 extends AbstractGenerator {

    /**
     * @inheritDoc
     */
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

    /**
     * @inheritDoc
     */
    public void generate() {
        int[] sizes = {15, 50, 200, 500, 1000, 2000, 5000, (int) 1e4, (int) (2.5 * 1e4), 5 * (int) 1e4, (int) (7.5 * 1e4), (int) 1e5};
        for (int n : sizes) {
            double[][] matrix = generateMatrix(n);
            printMatrix(matrix, 3, n, -1);
        }
    }
}
