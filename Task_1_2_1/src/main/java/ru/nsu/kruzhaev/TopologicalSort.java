package ru.nsu.kruzhaev;

import java.util.List;

/**
 * Интерфейс для топологической сортировки.
 */
public interface TopologicalSort {

    /**
     * Метод возвращающий топологически отсортированный список вершин графа.
     *
     * @return Результат топологической сортировки.
     */
    public List<Vertex> getRes();
}
