package ru.nsu.kruzhaev;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Класс реализующий топологическую сортировку при помощи алгоритма Кана.
 */
public class KahnSort implements TopologicalSort{
    private List<Vertex> res;
    private Queue<Vertex> queue;
    private Set<Vertex> vertices;
    private Map<Vertex, Integer> verticesParentsNumbers;

    /**
     * Конструктор класса {@code KahnSort}/
     *
     * @param graph Граф для сортировки.
     */
    public KahnSort(Graph graph) {
        res = new ArrayList<>();
        queue = new ArrayDeque<>();
        verticesParentsNumbers = new HashMap<>();
        vertices = graph.getVertices();

        sort();
    }

    /**
     * Сортирующий метод.
     */
    private void sort() {
        for (Vertex v : vertices) {
            int n = v.getParents().size();
            verticesParentsNumbers.put(v, n);
            if (n == 0) {
                queue.add(v);
            }
        }

        while (!queue.isEmpty()) {
            Vertex vrtx = queue.poll();
            res.add(vrtx);
            for (Vertex v : vrtx.getChildren()) {
                int n = verticesParentsNumbers.get(v);
                verticesParentsNumbers.put(v, --n);
                if (n == 0) {
                    queue.add(v);
                }
            }
        }

        if (res.size() != vertices.size()) {
            throw new RuntimeException("Обнаружен цикл!!!");
        }
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
