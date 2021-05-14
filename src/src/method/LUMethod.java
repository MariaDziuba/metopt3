package src.method;

import src.Matrix;
import src.ProfileSLAEMatrix;
import src.LU;

public class LUMethod {

    double EPS = 1e-20;
    ProfileSLAEMatrix matrix;
    double[] b;
    double[] y;
    double[] x;
    public long actions = 0;

    public LUMethod(ProfileSLAEMatrix matrix, double[] b) {
        this.matrix = matrix;
        this.b = b;
        this.y = new double[matrix.size()];
        this.x = new double[matrix.size()];
    }

    // Ly = b
    private void findY(Matrix L) {
        for (int i = 0; i < matrix.size(); i++) {
            y[i] = b[i];
            for (int j = matrix.firstInProfileL(i); j < i; j++) {
                y[i] -= L.get(i, j) * y[j];
                actions ++;
            }
        }
    }

    // Ux = y
    private void findX(Matrix U) {
        for (int i = matrix.size() - 1; i > -1; i--) {
            x[i] = y[i];
            for (int j = matrix.size() - 1; j > i; j--) {
                x[i] -= U.get(i, j) * x[j];
                actions ++;
            }
            x[i] /= U.get(i, i);
            actions++;
        }
    }

    public double[] findSolutions() {
        Matrix[] LU = new LU(matrix).getLU();
        Matrix L = LU[0];
        Matrix U = LU[1];

        for (int i = 0; i < matrix.size(); i++) {
            if (Math.abs(U.get(i, i)) < EPS) {
                return null;
            }
        }

        findY(L);
        findX(U);

        return x;
    }
}
