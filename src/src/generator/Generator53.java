package src.generator;

public class Generator53 extends AbstractGenerator {
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
            // sum instead of -sum
            matrix[i][i] = sum;
            if (i == 0) {
                // -=1 instead of +=1
                matrix[i][i] -= 1;
            }
        }
        return matrix;
    }

    public void generate() {
        double[][] matrix;
        int[] sizes = {15, 50, 200, 500, 1000, 2000, 5000, (int) 1e4, (int) (2.5 * 1e4), 5 * (int) 1e4, (int) (7.5 * 1e4), (int) 1e5};
        for (int n : sizes) {
            matrix = generateMatrix(n);
            printMatrix(matrix, 53, n, -1);
        }

    }
}
