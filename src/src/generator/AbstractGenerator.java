package src.generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractGenerator {

    public double[] multiplyOnVectorX(double[][] matrix) {
        int n = matrix.length;
        double[] f = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                f[i] += (j + 1) * matrix[i][j];
            }
        }
        return f;
    }

    public void printMatrix(double[][] matrix, int exercise, int n, int k) {
        double[] f = multiplyOnVectorX(matrix);
        String matrixPath =  "src/matrices" + "/" + exercise + (exercise == 2 ? "/k" + k : "") + "/n" + n + ".txt";
        Path path = Paths.get(matrixPath);
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException ignored) {
        }
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(String.valueOf(n));
            bw.newLine();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    bw.write(matrix[i][j] + " ");
                }
                bw.write("\n");
            }
            bw.write("\n");
            for (int i = 0; i < n; i++) {
                bw.write(f[i] + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void generate();
    public abstract double[][] generateMatrix(int n);
}
