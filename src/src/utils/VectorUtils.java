package src.utils;

import src.matrix.SparseSLAEMatrix;

import java.util.Arrays;
import java.util.stream.IntStream;

public class VectorUtils {

    public static double[] multiplyVectorOnScalar(SparseSLAEMatrix matrix, double[] vect) {
        int l = matrix.getColumnNumbers();
        double[] res = new double[l];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                res[i] += matrix.get(i, j) * vect[j];
            }
        }
        return res;
    }

    public static double[] subtractVectors(double[] vect1, double[] vect2) {
        double[] res = Arrays.copyOf(vect1, vect1.length);
        IntStream.range(0, vect1.length).forEach(i -> res[i] -= vect2[i]);
        return res;
    }

    public static double scalarProduct(double[] vect1, double[] vect2) {
        double sum = 0;
        for (int i = 0; i < vect1.length; i++) {
            sum += vect1[i] * vect2[i];
        }
        return sum;
    }

    public static double[] sumVectors(double[] vect1, double[] vect2) {
        double[] res = new double[vect1.length];
        for (int i = 0; i < vect1.length; i++) {
            res[i] = vect2[i] + vect1[i];
        }
        return res;
    }

    public static double[] multiplyVectorOnScalar(double sc, double[] vect) {
        double[] res = new double[vect.length];
        for (int i = 0; i < vect.length; i++) {
            res[i] = vect[i] * sc;
        }
        return res;
    }
}
