package src;

public class LUMethod {
    ProfileSLAEMatrix matrix;
    double[] b;
    double[] y;
    double[] x;

    public LUMethod(ProfileSLAEMatrix matrix, double[] b) {
        this.matrix = matrix;
        this.b = b;
        this.y = new double[matrix.size()];
        this.x = new double[matrix.size()];
    }


    // Ly = b
    private void findY() {
        int size = matrix.size();
        for (int i = 0; i < size; i++) {
            double sum = 0;
            for (int p = 0; p < i; p++) {
                sum += matrix.getFromL(i, p) * y[p];
            }
            y[i] = b[i] - sum;
        }
    }

    // Ux = y
    private void findX() {
        int size = matrix.size();
        double[] x = new double[size];
        for (int i = 0; i < size; i++) {
            double sum = 0;
            for (int p = 0; p < i; p++) {
                sum += matrix.getFromU(size - i - 1, size - p - 1) * x[size - p - 1];
            }
            x[size - i - 1] = (y[size - i - 1] - sum) / matrix.getFromU(size - i - 1, size - i - 1);
        }
    }

    public double[] findSolutions() {
        matrix.LUDecomposition();

        findY();
        findX();

        return x;
    }
}
