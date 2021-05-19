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
        for (int n = 15; n < 1000; n += 50) {
            for (int k = 0; k < 7; k++) {
                double[][] matrix = generateMatrix(n);
                printMatrix(matrix, true, n, k);
            }
        }
    }
}
