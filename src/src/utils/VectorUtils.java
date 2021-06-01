package src.utils;

import src.matrix.SparseSLAEMatrix;

/**
 * Операции над векторами
 */
public class VectorUtils {

    //ok
    /**
     * Вычитание векторов
     */
    public static double[] subtractVectors(double[] vect1, double[] vect2) {
        double[] res = new double[vect1.length];
        for (int i = 0; i < vect1.length; i++) {
            res[i] = vect1[i] - vect2[i];
        }
        return res;
    }

    //ok
    /**
     * Скалярное произведение векторов
     */
    public static double scalarProduct(double[] vect1, double[] vect2) {
        double sum = 0;
        for (int i = 0; i < vect1.length; i++) {
            sum += vect1[i] * vect2[i];
        }
        return sum;
    }

    //ok
    /**
     * Сложение векторов
     */
    public static double[] sumVectors(double[] vect1, double[] vect2) {
        double[] res = new double[vect1.length];
        for (int i = 0; i < vect1.length; i++) {
            res[i] = vect2[i] + vect1[i];
        }
        return res;
    }

    //ok
    /**
     * Умножение вектора на скаляр
     */
    public static double[] multiplyVectorOnScalar(double sc, double[] vect) {
        double[] res = new double[vect.length];
        for (int i = 0; i < vect.length; i++) {
            res[i] = vect[i] * sc;
        }
        return res;
    }
}
