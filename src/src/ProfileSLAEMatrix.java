package src;

public class ProfileSLAEMatrix implements Matrix {

    private double[] d;
    private double[] al;
    private double[] au;
    private int[] ial;
    private int[] iau;
    private boolean isLU = false;

    int size;

    public ProfileSLAEMatrix(double[][] matrix) {
        size = matrix.length;
        makeD(matrix);
        makeIal(matrix, size);
        makeAl(matrix);
        makeIau(matrix, size);
        makeAu(matrix);
    }

    private void makeD(double[][] matrix) {
        d = new double[size];
        for (int i = 0; i < size; i++) {
            d[i] = matrix[i][i];
        }
    }

    private void makeAl(double[][] matrix) {
        al = new double[ial[size]];
        for (int i = 0; i < size; i++) {
            int start = ial[i] + i - ial[i + 1];
            if (ial[i + 1] - ial[i] >= 0) {
                System.arraycopy(matrix[i], start, al, ial[i], ial[i + 1] - ial[i]);
            }
        }
    }

    private void makeAu(double[][] matrix) {
        au = new double[iau[size]];
        for (int i = 0; i < size; i++) {
            int start = iau[i] + i - iau[i + 1];
            for (int j = 0; j < iau[i + 1] - iau[i]; j++) {
                au[iau[i] + j] = matrix[start + j][i];
            }
        }
    }


    private int[] makeProfile(double[][] matrix, int size, boolean isBuildIal) {
        int[] res = new int[size + 1];
        res[0] = 0;
        res[1] = 0;
        for (int i = 1; i < size; i++) {
            int start = 0;
            while (start < i && (isBuildIal && matrix[i][start] == 0 || !isBuildIal && matrix[start][i] == 0)) {
                start++;
            }
            res[i + 1] = res[i] + (i - start);
        }
        return res;
    }

    private void makeIal(double[][] matrix, int size) {
        ial = makeProfile(matrix, size, true);
    }

    private void makeIau(double[][] matrix, int size) {
        iau = makeProfile(matrix, size, false);
    }

    public ProfileSLAEMatrix(double[] d, double[] al, int[] ial, double[] au, int[] iau) {
        this.d = d;
        this.al = al;
        this.ial = ial;
        this.au = au;
        this.iau = iau;
    }

    public int size() {
        return d.length;
    }

    public double get(int i, int j) {
        if (isLU) {
            throw new UnsupportedOperationException("LU modification was made");
        }
        if (i == j) {
            return d[i];
        }
        if (i > j) {
            return get(al, ial, i, j);
        }
        return get(au, iau, j, i);
    }

    public double getFromL(int i, int j) {
        if (!isLU) {
            throw new UnsupportedOperationException("LU modification wasn't made");
        }
        if (i == j) {
            return d[i];
        }
        if (i > j) {
            return get(al, ial, i, j);
        }
        return 0.0;
    }

    public double getFromU(int i, int j) {
        if (!isLU) {
            throw new UnsupportedOperationException("LU modification wasn't made");
        }
        if (i == j) {
            return 1.0;
        }
        if (i < j) {
            return get(au, iau, j, i);
        }
        return 0.0;
    }

    private double get(double[] matrix, int[] idxs, int i, int j) {
        int size = idxs[i] - idxs[i - 1];
        int idx = j - (i - size + 1);
        if (idx < 0) {
            return 0;
        }
        return matrix[idxs[i - 1] + 1 + idx];
    }

    private void set(double[] matrix, int[] idxs, int i, int j, double val) {
        if (i == j) {
            d[i] = val;
//          added return:
            return;
        }
        int size = idxs[i] - idxs[i - 1];
        int idx = j - (i - size + 1);
//      todo("Index -1 out of bounds for length 9")
        matrix[idxs[i - 1] + 1 + idx] = val;
//        matrix[-(idxs[i - 1] + 1 + idx)] = val; have "Index 2 out of bounds for length 2"

//      Anton's boys' code:
/*
        int prof = idxs[i + 1] - idxs[i];
        int zeros = i - prof;
        if (j >= zeros) {
            matrix[idxs[i] + (j - zeros) - 1] = val;
        }
*/
    }

    public void LUDecomposition() {
        if (isLU) {
            throw new UnsupportedOperationException("LU modification was made");
        }
        for (int i = 1; i < size(); i++) {
            for (int j = 0; j <= i; j++) {
                double sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += this.get(i, k) * this.get(k, j);
                }
                set(al, ial, i, j, this.get(i, j) - sum);
            }
            for (int j = 0; j < i; j++) {
                double sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += this.get(j, k) * this.get(k, i);
                }
                set(au, iau, i, j, (this.get(j, i) - sum) / this.get(j, j));
            }
        }
        this.isLU = true;
    }
}