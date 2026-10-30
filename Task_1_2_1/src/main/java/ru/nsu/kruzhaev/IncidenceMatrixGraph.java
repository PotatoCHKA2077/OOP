package ru.nsu.kruzhaev;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Класс реализации графа при помощи матрицы инцидентности,
 * имплементированный от интерфейса {@code Graph}.
 */
public class IncidenceMatrixGraph implements Graph {
    private Map<Vertex, Map<Edge, Integer>> matrix;
    private Set<Edge> edges;

    /**
     * Конструктор пустого класса {@code IncidenceMatrixGraph}.
     */
    public IncidenceMatrixGraph() {
        matrix = new HashMap<>();
        this.edges = new HashSet<>();
    }

    /**
     * Конструктор класса {@code IncidenceMatrixGraph} со считыванием файла.
     *
     * @param filename файл с информацией о графе
     */
    public IncidenceMatrixGraph(String filename) {
        matrix = new HashMap<>();
        this.edges = new HashSet<>();

        readFromFile(filename);
    }

    /**
     * Конструктор класса {@code IncidenceMatrixGraph} сос списками вершин и рёбер.
     *
     * @param vertices список вершин для графа.
     * @param edges список рёбер для графа.
     */
    public IncidenceMatrixGraph(List<Vertex> vertices, List<Edge> edges) {
        matrix = new HashMap<>();
        this.edges = new HashSet<>();

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

        matrix.put(vertex, new HashMap<>());
        for (Edge edge : edges) {
            matrix.get(vertex).put(edge, 0);
        }
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
        matrix.remove(vertex);

        Set<Edge> cpedges = Set.copyOf(edges);
        for (Edge edge : cpedges) {
            if (Objects.equals(edge.getStart(), vertex)
                    || Objects.equals(edge.getEnd(), vertex)) {
                removeEdge(edge);
            }
        }
    }

    /**
     * Метод проверяющий вершину на {@code null}.
     *
     * @param vrtx Объект класса {@code Vertex}.
     */
    private void vertexNullCheck(Vertex vrtx) {
        if (vrtx == null) {
            throw new NullPointerException("Вершина отсутствует в графе");
        }
    }

    /**
     * Метод, проверяющий корректность ребра перед добавлением его в граф.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     */
    private void checkEdgeBeforeAdd(Edge edge) {
        vertexNullCheck(edge.getStart());
        vertexNullCheck(edge.getEnd());

        if (hasEdge(edge)) {
            throw new IllegalArgumentException("Ребро " + edge.toString() + "уже есть в графе!");
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
            throw new IllegalArgumentException("Ребро " + edge.toString()
                    + " отсутствует в графе!");
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

        matrix.get(start).put(edge, -1);
        matrix.get(end).put(edge, 1);
        edges.add(edge);
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

        for (Vertex vrtx : matrix.keySet()) {
            matrix.get(vrtx).remove(edge);
        }

        edges.remove(edge);
    }

    /**
     * Метод возвращающий множество всех вершин графа.
     *
     * @return Множество, содержащее все вершины графа.
     */
    public Set<Vertex> getVertices() {
        return Set.copyOf(matrix.keySet());
    }

    /**
     * Метод возвращающий множество всех рёбер графа.
     *
     * @return Множество, содержащее все рёбра графа.
     */
    public Set<Edge> getEdges() {
        return Set.copyOf(edges);
    }

    /**
     * Метод, проверяющий наличие ребра вершины в графе.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     * @return {@code true}, если вершина есть в графе, {@code false}, если нет.
     */
    public boolean hasEdge(Edge edge) {
        return edges.contains(edge);
    }

    /**
     * Метод, ищущий вершину в графе.
     *
     * @param vertex Строковое значение вершины (имя).
     * @return Найденная вершина {@code Vertex}, {@code null} если вершина не найдена.
     */
    public Vertex findVertex(String vertex) {
        for (Vertex vrtx : matrix.keySet()) {
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
