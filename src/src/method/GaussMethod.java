package src.method;

/**
 * Метод Гаусса с выбором главного элемента (наибольшего по модулю)
 * (чтобы избежать сильного влияния вычислительной погрешности на решение)
 * Это метод последовательного исключения переменных, когда с помощью
 * элементарных преобразований система уравнений приводится к равносильной
 * системе треугольного вида, из которой последовательно, начиная с
 * последних (по номеру), находятся все переменные системы.
 *
 * Выбираем столбец слева направо. В текущем столбце находим сточку,
 * у которой элемент в данном столбце наибольший, преобразуем этот элемент
 * в единицу делением всей строки на его величину. Переносим эту строчку
 * наверх. Затем обнуляем другие элементы текущего столбца – для этого
 * вычитаем из оставшихся строк выбранную строчку, умноженную на элемент,
 * который хотим занулить. Проделываем эту операцию n раз, не затрагивая
 * столбцы, которые мы уже обнулили, и строчки, которые уже были перенесены
 * наверх.
 */
public class GaussMethod implements Method {
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

    /**
     * @inheritDoc
     */
    @Override
    public double[] findSolutions() {
        actions = 0;
        if (!makeUpperTriangular()) {
            return null;
        }

        backSubstitution();

        return x;
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getActions() {
        return actions;
    }

    /**
     * Приводит матрицу к треугольному виду
     */
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

    /**
     * ведущий элемент
     */
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
