package src.method;

import src.matrix.SparseSLAEMatrix;
import static src.utils.VectorUtils.*;

public class ConjugateMethod implements Method {

    SparseSLAEMatrix sparseMatrix;
    double[] f;
    long actions = 0;

    public ConjugateMethod(SparseSLAEMatrix sparseMatrix, double[] f) {
        this.sparseMatrix = sparseMatrix;
        this.f = f;
    }

    @Override
    public double[] findSolutions() {
//        todo(count actions!!!)
        actions = 0;
        double[] x0 = new double[f.length];
        x0[0] = 1;
        double[] r0 = subtractVectors(f, sparseMatrix.smartMultiplication(x0));
        double[] z0 = r0;
        int MAX_ITERATIONS = 3000;
        for (int k = 1; k < MAX_ITERATIONS; k++) {
            double[] Az0 = sparseMatrix.smartMultiplication(z0);
            double alphaK = scalarProduct(r0, r0) / scalarProduct(Az0, z0);
            actions++;

            double[] xK = sumVectors(x0, multiplyVectorOnScalar(alphaK, z0));
            double[] rK = subtractVectors(r0, multiplyVectorOnScalar(alphaK, Az0));

            double betaK = scalarProduct(rK, rK) / scalarProduct(r0, r0);
            actions++;
            double[] zK = sumVectors(rK, multiplyVectorOnScalar(betaK, z0));
            if (Math.sqrt(scalarProduct(rK, rK) / scalarProduct(f, f)) < EPS) {
                return xK;
            }
            actions++;
            x0 = xK;
            z0 = zK;
            r0 = rK;
        }
        return x0;
    }

    @Override
    public long getActions() {
        return 0;
    }
}