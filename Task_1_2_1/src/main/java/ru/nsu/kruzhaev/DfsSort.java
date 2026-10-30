package ru.nsu.kruzhaev;

import java.util.*;

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
    private Stack<Vertex> zeroParents;

    /**
     * Конструктор класса {@code DFSSort}.
     *
     * @param graph Граф для сортировки.
     */
    public DfsSort(Graph graph) {
        res = new ArrayList<>();
        verticesColors = new HashMap<>();
        vertices = graph.getVertices();
        zeroParents = new Stack<>();

        sort();
    }

    /**
     * Сортирующий метод.
     */
    private void sort() {
        for (Vertex v : vertices) {
            if (v.getParents().isEmpty()) {
                zeroParents.add(v);
            }

            verticesColors.put(v, white);
        }

        if (zeroParents.isEmpty()) {
            throw new RuntimeException("Что-то пошло не так!");
        }

        zeroParents.sort(Comparator.comparing(Vertex::getName));
        int s = zeroParents.size();
        for (int i = 0; i < s; i++) {
            coloring(zeroParents.pop());
        }

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
