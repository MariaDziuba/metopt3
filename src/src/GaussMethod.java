package src;

public class GaussMethod {

    double EPS = 1e-14;
    double[][] denseMatrix;
    double[] b;
    int size;
    double[] x;

    public GaussMethod(double[][] denseMatrix, double[] b, int size) {
        this.denseMatrix = denseMatrix;
        this.b = b;
        this.size = size;
    }

    public double[] solve() {
        if (!makeUpperTriangular()) {
            return null;
        }
        
        backSubstitution();
        
        return x;
    }

    boolean makeUpperTriangular() {
        for (int k = 0; k < size; k++) {
            findLineWithLeadElement(k);
            if (Math.abs(denseMatrix[k][k]) < EPS) {
                return false;
            }
            for (int i = k + 1; i < size; i++) {
                double coeff = denseMatrix[i][k] / denseMatrix[k][k];
                for (int j = k; j < size; j++) {
                    denseMatrix[i][j] -= denseMatrix[k][j] * coeff;
                }
                b[i] -= b[k] * coeff;
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
            }
            x[i] /= denseMatrix[i][i];
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
