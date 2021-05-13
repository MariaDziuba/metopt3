package src;

public class ProfileSLAEMatrix {

    private final double[] d;
    private final double[] al;
    private final double[] au;
    private final int[] ial;
    private final int[] iau;
    private boolean isLU = false;

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

    public double get(int i, int j)  {
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
        }
        int size = idxs[i] - idxs[i - 1];
        int idx = j - (i - size + 1);
        matrix[idxs[i - 1] + 1 + idx] = val;
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
