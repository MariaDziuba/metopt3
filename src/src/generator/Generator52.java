package src.generator;

import java.io.BufferedWriter;

/**
 * Генератор для задания 5.2
 */
public class Generator52 extends AbstractGenerator {

    /**
     * @inheritDoc
     */
    public double[][] generateMatrix(int n) {
        double[] matrix = new double[n];
        BufferedWriter bw = getBW(52, n, -1);
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int el = (int) (Math.random() * 5) * -1;
                    sum += el;
                    matrix[j] = el;
                }
            }
            matrix[i] = -sum;
            if (i == 0) {
                matrix[i] += 1;
            }
            writeLine(bw, matrix);
        }
        return new double[0][];
    }

    /**
     * @inheritDoc
     */
    public void generate() {
        int[] sizes = {/*15, 50, 200, 500, 1000, 2000, 5000,*/ (int) 1e4, (int) (2.5 * 1e4), 5 * (int) 1e4, (int) (7.5 * 1e4), (int) 1e5};
        for (int n : sizes) {
            generateMatrix(n);
        }
    }
}
