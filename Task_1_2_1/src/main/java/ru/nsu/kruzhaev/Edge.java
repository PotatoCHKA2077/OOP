package ru.nsu.kruzhaev;

/**
 * Класс {@code Edge}.
 */
public class Edge {
    private final Vertex start;
    private final Vertex end;

    /**
     * Конструктор класса {@code Edge}.
     *
     * @param start Строковое значение исходной вершины ребра.
     * @param end Строковое значение входной вершины ребра.
     */
    public Edge(String start, String end) {
        this.start = new Vertex(start);
        this.end = new Vertex(end);
    }

    /**
     * Конструктор класса {@code Edge}.
     *
     * @param start Объект {@code Vertex} исходной вершины ребра.
     * @param end Объект {@code Vertex} входной вершины ребра.
     */
    public Edge(Vertex start, Vertex end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Метод возвращающий исходящую вершину.
     *
     * @return Исходящая вершина.
     */
    public Vertex getStart() {
        return start;
    }

    /**
     * Метод возвращающий входящую вершину.
     *
     * @return Входящая вершина.
     */
    public Vertex getEnd() {
        return end;
    }

    /**
     * Метод переопределяющий метод {@code toString}.
     *
     * @return Строковая реализация ребра.
     */
    @Override
    public String toString() {
        return start.getName() + " -> " + end.getName();
    }

    /**
     * Переопределение метода {@code hashCode}.
     *
     * @return Сумма хеша исходной вершины и удвоенного хеша входной вершины.
     */
    @Override
    public int hashCode() {
        int result = start.getName() == null ? 0 : start.getName().hashCode();
        result += end.getName() == null ? 0 : 2 * end.getName().hashCode();
        return result;
    }

    /**
     * Переопределение метода {@code equals}.
     *
     * @return Результат сравнения объектов.
     */
    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }

        Vertex ostart = ((Edge) o).start;
        Vertex oend = ((Edge) o).end;

        return start.equals(ostart) && end.equals(oend);
    }
}
