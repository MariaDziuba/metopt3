package src.method;

public class GaussMethod implements Method{
    long actions = 0;
    int size;

    double[][] denseMatrix;
    double[] b;
    double[] x;

    public GaussMethod(double[][] denseMatrix, double[] b, int size) {
        this.denseMatrix = denseMatrix;
        this.b = b;
        this.size = size;
    }

    @Override
    public double[] findSolutions() {
        if (!makeUpperTriangular()) {
            return null;
        }

        backSubstitution();

        return x;
    }

    @Override
    public long getActions() {
        return actions;
    }

    boolean makeUpperTriangular() {
        for (int k = 0; k < size; k++) {
            findLineWithLeadElement(k);
            if (Math.abs(denseMatrix[k][k]) < EPS) {
                return false;
            }
            for (int i = k + 1; i < size; i++) {
                double coeff = denseMatrix[i][k] / denseMatrix[k][k];
                actions++;
                for (int j = k; j < size; j++) {
                    denseMatrix[i][j] -= denseMatrix[k][j] * coeff;
                    actions ++;
                }
                b[i] -= b[k] * coeff;
                actions ++;
            }
        }
        return true;
    }

    void backSubstitution() {
        x = new double[size];
        for (int i = size - 1; i > -1; i--) {
            x[i] = b[i];
            for (int j = i + 1; j < size; j++) {
                x[i] -= x[j] * denseMatrix[i][j];
                actions ++;
            }
            x[i] /= denseMatrix[i][i];
            actions++;
        }
    }

    private void findLineWithLeadElement(int k) {
        int leadElemIdx = k;
        for (int i = k; i < size; i++) {
            if (denseMatrix[i][k] > denseMatrix[leadElemIdx][k]) {
                leadElemIdx = i;
            }
        }

        double tmp1 = b[k];
        b[k] = b[leadElemIdx];
        b[leadElemIdx] = tmp1;

        for (int i = 0; i < size; i++) {
            double tmp = denseMatrix[leadElemIdx][i];
            denseMatrix[leadElemIdx][i] = denseMatrix[k][i];
            denseMatrix[k][i] = tmp;
        }
    }
}
