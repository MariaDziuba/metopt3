package src.matrix;

public class LU {

    ProfileSLAEMatrix matrix;

    public class L implements Matrix {
        @Override
        public double get(int i, int j) {
            if (i < j) {
                return 0;
            }
            if (i == j) {
                return 1;
            }
            return matrix.get(i, j);
        }
        @Override
        public int size() {
            return matrix.size();
        }
        @Override
        public void set(int i, int j, double val) {
            throw new IllegalArgumentException();
        }
    }

    public class U implements Matrix {
        @Override
        public double get(int i, int j) {
            if (i > j) {
                return 0;
            }
            return matrix.get(i, j);
        }
        @Override
        public int size() {
            return matrix.size();
        }
        @Override
        public void set(int i, int j, double val) {
            throw new IllegalArgumentException();
        }
    }

    public LU(ProfileSLAEMatrix matrix) {
        this.matrix = matrix;
    }
    public Matrix[] getLU() {
        for (int i = 1; i < matrix.size(); i++) {
            for (int j = matrix.firstInProfileL(i); j < i; j++) {
                double z = matrix.get(i, j);
                for (int k = 0; k < j; k++) {
                    z -= matrix.get(i, k) * matrix.get(k, j);
                }
                if (z != 0) {
                    z /= matrix.get(j, j);
                }
                matrix.set(i, j, z);
            }
            for (int j = matrix.firstInProfileU(i); j < i; j++) {
                double z = matrix.get(j, i);
                for (int k = 0; k < j; k++) {
                    z -= matrix.get(j, k) * matrix.get(k, i);
                }
                matrix.set(j, i, z);
            }
            double z = matrix.get(i, i);
            for (int k = 0; k < i; k++) {
                z -= matrix.get(i, k) * matrix.get(k, i);
            }
            matrix.set(i, i, z);
        }

        return new Matrix[] {new L(), new U()};
    }
}
