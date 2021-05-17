package src.matrix;

public class ProfileSLAEMatrix implements Matrix{

    private double[] d;
    private double[] al;
    private double[] au;
    private int[] ial, iau;

    public ProfileSLAEMatrix(double[][] matrix) {
        int size = matrix.length;

        makeD(matrix, size);
        makeIal(matrix, size);
        makeAl(matrix, size);
        makeIau(matrix, size);
        makeAu(matrix, size);
    }

    private void makeAl(double[][] matrix, int size) {
        al = new double[ial[size]];
        for (int i = 0; i < size; i++) {
            int beginning = ial[i] + i - ial[i + 1];
            if (ial[i + 1] - ial[i] >= 0) {
                System.arraycopy(matrix[i], beginning, al, ial[i], ial[i + 1] - ial[i]);
            }
        }
    }

    private void makeAu(double[][] matrix, int size) {
        au = new double[iau[size]];
        for (int i = 0; i < size; i++) {
            int beginning = iau[i] + i - iau[i + 1];
            for (int j = 0; j < iau[i + 1] - iau[i]; j++) {
                au[iau[i] + j] = matrix[beginning + j][i];
            }
        }
    }

    private void makeD(double[][] matrix, int size) {
        d = new double[size];
        for (int i = 0; i < size; i++) {
            d[i] = matrix[i][i];
        }
    }

    private int[] makeProfile(double[][] matrix, int size, boolean isIal) {
        int[] res = new int[size + 1];
        res[0] = 0;
        res[1] = 0;
        for (int i = 1; i < size; i++) {
            int beginning = 0;
            while (beginning < i && (isIal && matrix[i][beginning] == 0 || !isIal && matrix[beginning][i] == 0)) {
                beginning++;
            }
            res[i + 1] = res[i] + (i - beginning);
        }
        return res;
    }


    private void makeIal(double[][] matrix, int size) {
        ial = makeProfile(matrix, size, true);
    }


    private void makeIau(double[][] matrix, int size) {
        iau = makeProfile(matrix, size, false);
    }


    @Override
    public double get(int i, int j) {
        if (i == j) {
            return d[i];
        }
        if (i < j) {
            int beginning = j - (iau[j + 1] - iau[j]);
            if (i < beginning) {
                return 0;
            } else {
                return au[iau[j] + i - beginning];
            }
        } else {
            int beginning = i - (ial[i + 1] - ial[i]);
            if (j < beginning) {
                return 0;
            } else {
                return al[ial[i] + j - beginning];
            }
        }
    }

    @Override
    public void set(int i, int j, double val) {
        if (i == j) {
            d[i] = val;
            return;
        }
        if (i < j) {
            int beginning = j - (iau[j + 1] - iau[j]);
            if (i < beginning) {
                throw new IllegalArgumentException("Is not in profile");
            } else {
                au[iau[j] + i - beginning] = val;
            }
        } else {
            int beginning = i - (ial[i + 1] - ial[i]);
            if (j < beginning) {
                throw new IllegalArgumentException("Is not in profile");
            } else {
                al[ial[i] + j - beginning] = val;
            }
        }
    }

    @Override
    public int size() {
        return d.length;
    }

    public int firstInProfileL(int i) {
        return i - (ial[i + 1] - ial[i]);
    }

    int firstInProfileU(int i) {
        return i - (iau[i + 1] - iau[i]);
    }
}