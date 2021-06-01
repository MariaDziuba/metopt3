package src.method;

/**
 * Интерфейс для метода решения СЛАУ
 */
public interface Method {

    // заданная точность
    double EPS = 1e-20;

    /**
     * Нахождение решений СЛАУ
     */
    double[] findSolutions();

    /**
     * Возвращает количество итераций
     */
    long getActions();
}
