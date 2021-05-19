package src.method;

import src.matrix.Matrix;
import src.matrix.ProfileSLAEMatrix;
import src.matrix.LU;

public class LUMethod implements Method{

    double EPS = 1e-20;
    ProfileSLAEMatrix profileMatrix;
    double[] b;
    double[] y;
    double[] x;
    long actions = 0;

    public LUMethod(ProfileSLAEMatrix profileMatrix, double[] b) {
        this.profileMatrix = profileMatrix;
        this.b = b;
        this.y = new double[profileMatrix.size()];
        this.x = new double[profileMatrix.size()];
    }

    // Ly = b
    private void findY(Matrix L) {
        for (int i = 0; i < profileMatrix.size(); i++) {
            y[i] = b[i];
            for (int j = profileMatrix.firstInProfileL(i); j < i; j++) {
                y[i] -= L.get(i, j) * y[j];
                actions ++;
            }
        }
    }

    // Ux = y
    private void findX(Matrix U) {
        for (int i = profileMatrix.size() - 1; i > -1; i--) {
            x[i] = y[i];
            for (int j = profileMatrix.size() - 1; j > i; j--) {
                x[i] -= U.get(i, j) * x[j];
                actions ++;
            }
            x[i] /= U.get(i, i);
            actions++;
        }
    }

    @Override
    public double[] findSolutions() {
        Matrix[] LU = new LU(profileMatrix).getLU();
        Matrix L = LU[0];
        Matrix U = LU[1];

        for (int i = 0; i < profileMatrix.size(); i++) {
            if (Math.abs(U.get(i, i)) < EPS) {
                return null;
            }
        }

        findY(L);
        findX(U);

        return x;
    }

    @Override
    public long getActions() {
        return actions;
    }
}
