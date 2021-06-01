package src.generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Абстрактный класс для генерации матриц
 */
public abstract class AbstractGenerator {

    /**
     * Умножает данную матрицу на заранее известный вектор Х
     */
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

    /**
     * Умножает данную матрицу на данный вектор
     */
    public double[] multiplyOnVector(double[][] matrix, double[] vector) {
        int n = matrix.length;
        double[] f = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                f[i] += vector[j] * matrix[i][j];
            }
        }
        return f;
    }

    /**
     * Печатает матрицу для данного номера задания
     * n - размер матрицы
     * k - заданный параметр генерации
     */
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

    /**
     * Возвращает текущий {@link BufferedWriter}
     */
    public BufferedWriter getBW(int exercise, int n, int k) {
        String matrixPath =  "src/matrices" + "/" + exercise + (exercise == 2 ? "/k" + k : "") + "/n" + n + ".txt";
        Path path = Paths.get(matrixPath);
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException ignored) {
        }
        BufferedWriter bw;
        try {
            bw = Files.newBufferedWriter(path);
            bw.write(String.valueOf(n));
            bw.newLine();
            return bw;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Печатает вектор в строку
     */
    public void writeLine(BufferedWriter bw, double[] vector) {
        try {
            for (double v : vector) {
                bw.write(v + " ");
            }
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрывает данный {@link BufferedWriter}
     */
    public void closeBW(BufferedWriter bw) {
        try {
            bw.write("\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Генерирует всё, что необходимо для текущего задания
     */
    public abstract void generate();

    /**
     * Генерирует матрицу
     * @param n - размер матрицы
     */
    public abstract double[][] generateMatrix(int n);
}
