package src.matrix;

import java.util.ArrayList;
import java.util.List;

public class SparseSLAEMatrix implements Matrix{

    private double EPS = 1e-14;
    private double[] al;
    private double[] au;
    private double[] d;
    private int[] ia;
    private int[] ja;
    private int n;

    public SparseSLAEMatrix(double[][] matrix) {
        n = matrix.length;
        d = new double[matrix.length];
        ia = new int[matrix.length + 2];
        ArrayList<Double> alList = new ArrayList<>();
        ArrayList<Double> auList = new ArrayList<>();
        ArrayList<Integer> jaList = new ArrayList<>();
        makeD(matrix);
        makeIa(matrix, alList, auList, jaList);
        al = new double[alList.size()];
        au = new double[auList.size()];
        ja = new int[jaList.size()];
        makeAu(auList);
        makeAl(alList);
        makeJa(jaList);
    }

    private void makeD(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            d[i] = matrix[i][i];
        }
    }


    private void makeIa(double[][] matrix, ArrayList<Double> alList, ArrayList<Double> auList,
                        ArrayList<Integer> jaList) {
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 1; i < matrix.length; i++) {
            ia[i + 1] = ia[i] + getRowProfileLen(matrix, i, alList, auList, jaList);
        }
    }


    private void makeAl(ArrayList<Double> alList) {
        for (int i = 0; i < alList.size(); i++) {
            al[i] = alList.get(i);
        }
    }

    private void makeAu(ArrayList<Double> auList) {

        for (int i = 0; i < auList.size(); i++) {
            au[i] = auList.get(i);
        }
    }

    private void makeJa(ArrayList<Integer> jaList) {
        for (int i = 0; i < jaList.size(); i++) {
            ja[i] = jaList.get(i);
        }
    }



    @Override
    public double get(int i, int j) {
        if (i == j) {
            return d[i];
        }
        boolean f = true;
        if (j > i) {
            int tmp = j;
            j = i;
            i = tmp;
            f = false;
        }
        int countInRow = ia[i + 1] - ia[i];
        List<Integer> getAllColInRow = new ArrayList<>();
        for (int z = ia[i]; z < ia[i] + countInRow; z++) {
            getAllColInRow.add(ja[z]);
        }
        if (getAllColInRow.contains(j)) {
            if (f) {
                return al[ia[i] + getAllColInRow.indexOf(j)];
            } else {
                return au[ia[i] + getAllColInRow.indexOf(j)];
            }
        } else {
            return 0;
        }
    }

    @Override
    public int size() {
        return d.length;
    }

    @Override
    public void set(int i, int j, double val) {
        throw new UnsupportedOperationException();
    }

    private int getRowProfileLen(double[][] matrix, int row, List<Double> alList, List<Double> auList, List<Integer> jaList) {
        int idx = 0;
        int cnt = 0;
        while (idx != row) {
            if (!(Math.abs(matrix[row][idx] - 0) < EPS)) {
                alList.add(matrix[row][idx]);
                auList.add(matrix[idx][row]);
                jaList.add(idx);
                cnt++;
            }
            idx++;
        }
        return cnt;
    }

    public double[] smartMultiplication(double[] vector) {
        int leftBorderInJa = 0;
        double[] res = new double[vector.length];
        for (int i = 0; i < n; i++) {
            int cnt = ia[i + 1] - ia[i];
            res[i] += d[i] * vector[i];
            for (int j = 0; j < cnt; j++) {
                int col = ja[leftBorderInJa + j];
                res[i] += al[leftBorderInJa + j] * vector[col];
                res[col] += au[leftBorderInJa + j] * vector[i];
            }
            leftBorderInJa += cnt;
        }
        return res;
    }

    public int getColumnNumbers() {
        return n;
    }

    public int getRowNumbers() {
        return n;
    }

    /**
     * Костыль чтобы менять d[i]
     */
    public void replaceD(int i, double x) {
        d[i] = x;
    }
}
