package src.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Разреженная-строчно столбцовая матрица
 */
public class SparseSLAEMatrix implements Matrix {

    private double EPS = 1e-14;
    // внедиагональные элементы
    private final List<Double> al;
    private final List<Double> au;
    // диагональные элементы
    private final List<Double> d;
    // индексы, с которого начинаются элементы k-ой строки/столбца
    // в массивах ja, al, au
    private final List<Integer> ia;
    // номера столбцов/строк хранимых внедиагональных элементов
    // нижнего/верхнего треугольника матрицы
    private final List<Integer> ja;
    private int n;
    private int countIterations = 0;

    public SparseSLAEMatrix(final double[][] matrix) {
        n = matrix.length;
        this.d = makeD(matrix);
        this.ia = new ArrayList<>();
        final List<Double> all = new ArrayList<>();
        final List<Double> auu = new ArrayList<>();
        final List<Integer> jaa = new ArrayList<>();
        makeIa(matrix, all, auu, jaa);
        this.al = all;
        this.au = auu;
        this.ja = jaa;
    }

    private void makeIa(double[][] matrix,
                        List<Double> all,
                        List<Double> auu,
                        List<Integer> jaa) {
        ia.add(0);
        ia.add(0);
        for (int i = 1; i < matrix.length; i++) {
            ia.add(i + 1, ia.get(i) + getProfileLen(matrix, i, all, auu, jaa));
        }

    }

    /**
     * Возвращает длину профиля
     */
    private int getProfileLen(double[][] matrix,
                              int row,
                              List<Double> all,
                              List<Double> auu,
                              List<Integer> jaa) {
        int ans = 0;
        for (int i = 0; i < row; i++) {
            if (Math.abs(matrix[row][i]) >= EPS) {
                all.add(matrix[row][i]);
                auu.add(matrix[i][row]);
                jaa.add(i);
                ans++;
            }
        }
        return ans;
    }

    private List<Double> makeD(double[][] matrix) {
        final List<Double> b = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            b.add(matrix[i][i]);
        }
        return b;
    }

    //ok
    /**
     * Умножение на данный вектор
     */
    public double[] smartMultiplication(double[] vector) {
        int leftBorderInJa = 0;
        double[] res = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            res[i] = 0.0;
        }
        for (int i = 0; i < n; i++) {
            int cnt = ia.get(i + 1) - ia.get(i);
            res[i] += d.get(i) * vector[i];
            for (int j = 0; j < cnt; j++) {
                int col = ja.get(leftBorderInJa + j);
                res[i] += al.get(leftBorderInJa + j) * vector[col];
                res[col] += au.get(leftBorderInJa + j) * vector[i];
            }
            leftBorderInJa += cnt;
        }
        return res;
    }


    public int getIterations() {
        return countIterations;
    }

    /**
     * @inheritDoc
     */
    public double get(int i, int j) {
        if (i == j) {
            return d.get(i);
        }
        boolean f = true;
        if (j > i) {
            int tmp = j;
            j = i;
            i = tmp;
            f = false;
        }
        int countInRow = ia.get(i + 1) - ia.get(i);
        List<Integer> getAllColInRow = new ArrayList<>();
        for (int z = ia.get(i); z < ia.get(i) + countInRow; z++) {
            getAllColInRow.add(ja.get(z));
        }
        if (getAllColInRow.contains(j)) {
            if (f) {
                return al.get(ia.get(i) + getAllColInRow.indexOf(j));
            } else {
                return au.get(ia.get(i) + getAllColInRow.indexOf(j));
            }
        } else {
            return 0;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int size() {
        return n;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void set(int i, int j, double val) {
        throw new UnsupportedOperationException();
    }
}

