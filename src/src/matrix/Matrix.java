package src.matrix;

/**
 * Интерфейс матрицы
 */
public interface Matrix {

    /**
     * Возвращает элемент по индексу
     */
    double get(int i, int j);

    /**
     * Возвращает размер матрицы
     */
    int size();

    /**
     * Меняет текущее значение элемента на данное
     */
    void set(int i, int j, double val);
}
