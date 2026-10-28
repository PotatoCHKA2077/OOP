package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Класс реализующий топологическую сортировку при помощи алгоритма поиска в глубину.
 */
public class DfsSort implements TopologicalSort {
    private final int white = 0;
    private final int grey = 1;
    private final int black = 2;

    private List<Vertex> res;
    private final Set<Vertex> vertices;
    private Map<Vertex, Integer> verticesColors;

    /**
     * Конструктор класса {@code DFSSort}.
     *
     * @param graph Граф для сортировки.
     */
    public DfsSort(Graph graph) {
        res = new ArrayList<>();
        verticesColors = new HashMap<>();
        vertices = graph.getVertices();

        sort();
    }

    /**
     * Сортирующий метод.
     */
    private void sort() {
        Vertex vrtx = null;
        for (Vertex v : vertices) {
            if (v.getParents().isEmpty()) {
                vrtx = v;
            }

            verticesColors.put(v, white);
        }

        if (vrtx == null) {
            throw new RuntimeException("Что-то пошло не так!");
        }

        coloring(vrtx);

        res = res.reversed();
    }

    /**
     * Метод рекурсивно "красящий" вершину и её дочерние вершины.
     *
     * @param vertex Вершина для покраски.
     */
    private void coloring(Vertex vertex) {
        verticesColors.replace(vertex, grey);

        for (Vertex vrtx : vertex.getChildren()) {
            if (verticesColors.get(vrtx) == grey) {
                throw new RuntimeException("Найден цикл!!!");
            } else if (verticesColors.get(vrtx) == white) {
                coloring(vrtx);
            }
        }

        verticesColors.replace(vertex, black);
        res.add(vertex);
    }

    /**
     * Метод, возвращающий результат топологической сортировки.
     *
     * @return Список топологически отсортированных вершин графа.
     */
    public List<Vertex> getRes() {
        return res;
    }
}
