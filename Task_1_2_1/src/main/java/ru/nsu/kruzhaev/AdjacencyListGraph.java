package ru.nsu.kruzhaev;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Класс реализации графа при помощи списков смежности,
 * имплементированный от интерфейса {@code Graph}.
 */
public class AdjacencyListGraph implements Graph {
    private Map<Vertex, Set<Vertex>> verticesList;

    /**
     * Конструктор пустого класса {@code AdjacencyListGraph}.
     */
    public AdjacencyListGraph() {
        verticesList = new HashMap<>();
    }

    /**
     * Конструктор класса {@code AdjacencyListGraph} со считыванием файла.
     *
     * @param filename файл с информацией о графе
     */
    public AdjacencyListGraph(String filename) {
        verticesList = new HashMap<>();

        readFromFile(filename);
    }


    /**
     * Конструктор класса {@code AdjacencyListGraph} сос списками вершин и рёбер.
     *
     * @param vertices список вершин для графа.
     * @param edges список рёбер для графа.
     */
    public AdjacencyListGraph(List<Vertex> vertices, List<Edge> edges) {
        verticesList = new HashMap<>();

        for (Vertex vrtx : vertices) {
            addVertex(vrtx);
        }

        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    /**
     * Метод, добавляющий вершину в граф.
     *
     * @param vertex Вершина как объект класса вершины.
     */
    public void addVertex(Vertex vertex) {
        if (hasVertex(vertex.getName())) {
            throw new IllegalArgumentException("Такая вершина уже есть в графе!");
        }
        verticesList.put(vertex, new HashSet<>());
    }

    /**
     * Метод, удаляющий вершину из графа.
     *
     * @param vertex Вершина как объект класса {@code Vertex}.
     */
    public void removeVertex(Vertex vertex) {
        if (vertex == null || ! hasVertex(vertex.getName())) {
            throw new IllegalArgumentException("Такой вершины нет в графе!");
        }

        vertex.clean();
        verticesList.remove(vertex);

        for (Set<Vertex> vrtcs : verticesList.values()) {
            vrtcs.remove(vertex);
        }
    }

    /**
     * Метод, проверяющий корректность ребра перед добавлением его в граф.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     */
    private void checkEdgeBeforeAdd(Edge edge) {
        if (edge.getStart() == null || edge.getEnd() == null) {
            throw new NullPointerException("Одна или обе вершины ребра отсутствуют в графе!");
        }
        if (hasEdge(edge)) {
            throw new IllegalArgumentException("Такое ребро уже есть в графе!");
        }
    }

    /**
     * Метод, проверяющий корректность ребра перед удалением его из граф.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     */
    private void checkEdgeBeforeRemove(Edge edge) {
        if (edge.getStart() == null || edge.getEnd() == null) {
            throw new NullPointerException("Одна или обе вершины ребра отсутствуют в графе!");
        }
        if (!hasEdge(edge)) {
            throw new IllegalArgumentException("Такого ребра нет в графе!");
        }
    }

    /**
     * Метод, добавляющий ребро в граф.
     *
     * @param edge Ребро ка объект класса {@code Edge}.
     */
    public void addEdge(Edge edge) {
        checkEdgeBeforeAdd(edge);

        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.addChild(end);
        end.addParent(start);

        verticesList.get(start).add(end);
    }

    /**
     * Метод, удаляющий ребро из графа.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     */
    public void removeEdge(Edge edge) {
        checkEdgeBeforeRemove(edge);

        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.deleteChild(end);
        end.deleteParent(start);

        verticesList.get(start).remove(end);
    }

    /**
     * Метод возвращающий множество всех вершин графа.
     *
     * @return Множество, содержащее все вершины графа.
     */
    public Set<Vertex> getVertices() {
        return Set.copyOf(verticesList.keySet());
    }

    /**
     * Метод возвращающий множество всех рёбер графа.
     *
     * @return Множество, содержащее все рёбра графа.
     */
    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();

        for (Vertex vrtx : verticesList.keySet()) {
            for (Vertex v : verticesList.get(vrtx)) {
                edges.add(new Edge(vrtx, v));
            }
        }
        return edges;
    }

    /**
     * Метод, проверяющий наличие ребра вершины в графе.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     * @return {@code true}, если вершина есть в графе, {@code false}, если нет.
     */
    public boolean hasEdge(Edge edge) {
        return verticesList.containsKey(edge.getStart())
                && verticesList.get(edge.getStart()).contains(edge.getEnd());
    }

    /**
     * Метод, ищущий вершину в графе.
     *
     * @param vertex Строковое значение вершины (имя).
     * @return Найденная вершина {@code Vertex}, {@code null} если вершина не найдена.
     */
    public Vertex findVertex(String vertex) {
        for (Vertex vrtx : verticesList.keySet()) {
            if (Objects.equals(vrtx.getName(), vertex)) {
                return vrtx;
            }
        }
        return null;
    }

    /**
     * Переопределение метода {@code toString}.
     *
     * @return Строковую реализацию графа.
     */
    @Override
    public String toString() {
        return "Vertices: " + getVertices().toString()
                + "\nEdges: " + getEdges().toString();
    }

    /**
     * Переопределение метода {@code hashCode}.
     *
     * @return Сумма хеша множества вершин и удвоенного хеша множества рёбер.
     */
    @Override
    public int hashCode() {
        Set<Vertex> vrtcs = getVertices();
        int result = vrtcs == null ? 0 : vrtcs.hashCode();

        Set<Edge> edges = getEdges();
        result += edges == null ? 0 : 2 * edges.hashCode();

        return result;
    }

    /**
     * Переопределение метода {@code equals}.
     *
     * @return Результат сравнения объектов.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Graph graph)) {
            return false;
        }

        return getVertices().equals(graph.getVertices())
                && getEdges().equals(graph.getEdges());
    }
}
