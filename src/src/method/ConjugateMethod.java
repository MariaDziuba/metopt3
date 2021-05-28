package src.method;

import src.matrix.SparseSLAEMatrix;
import static src.utils.VectorUtils.*;

public class ConjugateMethod implements Method {

    private double EPS = 1e-14;

    SparseSLAEMatrix sparseMatrix;
    double[] f;
    long actions = 0;

    public ConjugateMethod(SparseSLAEMatrix sparseMatrix, double[] f) {
        this.sparseMatrix = sparseMatrix;
        this.f = f;
    }

    @Override
    public double[] findSolutions() {
        actions = 0;
        double[] x0 = new double[f.length];
        x0[0] = 1;
        for (int i = 1; i < f.length; i++) {
            x0[i] = 0.0;
        }
        double[] r0 = subtractVectors(f, sparseMatrix.smartMultiplication(x0));
        double[] z0 = r0;
        int MAX_ITERATIONS = 500;
        for (int k = 1; k < MAX_ITERATIONS; k++) {
            actions++;
            double[] Az0 = sparseMatrix.smartMultiplication(z0);
            double alphaK = scalarProduct(r0, r0) / scalarProduct(Az0, z0);

            double[] xK = sumVectors(x0, multiplyVectorOnScalar(alphaK, z0));
            double[] rK = subtractVectors(r0, multiplyVectorOnScalar(alphaK, Az0));

            double betaK = scalarProduct(rK, rK) / scalarProduct(r0, r0);
            double[] zK = sumVectors(rK, multiplyVectorOnScalar(betaK, z0));
            if (Math.sqrt(scalarProduct(rK, rK) / scalarProduct(f, f)) <= EPS) {
                return xK;
            }
            x0 = xK;
            z0 = zK;
            r0 = rK;
        }
        return x0;
    }

    @Override
    public long getActions() {
        return actions; // it's iterations
    }
}