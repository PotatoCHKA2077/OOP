package ru.nsu.kruzhaev;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс для графа.
 */
public interface Graph {

    /**
     * Метод, добавляющий вершину в граф.
     *
     * @param vertex Строковое значение вершины (имя).
     */
    public default void addVertex(String vertex) {
        addVertex(new Vertex(vertex));
    }

    /**
     * Метод, добавляющий вершину в граф.
     *
     * @param vertex Вершина как объект класса вершины.
     */
    public void addVertex(Vertex vertex);

    /**
     * Метод, удаляющий вершину из графа.
     *
     * @param vertex Строковое значение вершины (имя).
     */
    public default void removeVertex(String vertex) {
        removeVertex(findVertex(vertex));
    }

    /**
     * Метод, удаляющий вершину из графа.
     *
     * @param vertex Вершина как объект класса {@code Vertex}.
     */
    public void removeVertex(Vertex vertex);

    /**
     * Метод, добавляющий ребро в граф.
     *
     * @param start Строковое значение исходной вершины (имя).
     * @param end Строковое значение входной вершины (имя).
     */
    public default void addEdge(String start, String end) {
        addEdge(new Edge(
                findVertex(start),
                findVertex(end)));
    }

    /**
     * Метод, добавляющий ребро в граф.
     *
     * @param edge Ребро ка объект класса {@code Edge}.
     */
    public void addEdge(Edge edge);

    /**
     * Метод, удаляющий ребро из графа.
     *
     * @param start Строковое значение исходной вершины (имя).
     * @param end Строковое значение входной вершины (имя).
     */
    public default void removeEdge(String start, String end) {
        removeEdge(new Edge(
                findVertex(start),
                findVertex(end)));
    }

    /**
     * Метод, удаляющий ребро из графа.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     */
    public void removeEdge(Edge edge);

    /**
     * Метод возвращающий множество всех вершин графа.
     *
     * @return Множество, содержащее все вершины графа.
     */
    public Set<Vertex> getVertices();

    /**
     * Метод возвращающий множество всех рёбер графа.
     *
     * @return Множество, содержащее все рёбра графа.
     */
    public Set<Edge> getEdges();

    /**
     * Метод, проверяющий наличие вершины в графе.
     *
     * @param vertex Строковое значение вершины (имя).
     * @return {@code true}, если вершина есть в графе, {@code false}, если нет.
     */
    public default boolean hasVertex(String vertex) {
        Vertex v = findVertex(vertex);

        return v != null;
    }

    /**
     * Метод, проверяющий наличие ребра в графе.
     *
     * @param start Строковое значение исходной вершины (имя).
     * @param end Строковое значение входной вершины (имя).
     * @return {@code true}, если ребро есть в графе, {@code false}, если нет.
     */
    public default boolean hasEdge(String start, String end) {
        return hasEdge(new Edge(
                findVertex(start),
                findVertex(end)));
    }

    /**
     * Метод, проверяющий наличие ребра вершины в графе.
     *
     * @param edge Ребро как объект класса {@code Edge}.
     * @return {@code true}, если вершина есть в графе, {@code false}, если нет.
     */
    public boolean hasEdge(Edge edge);

    /**
     * Метод, ищущий вершину в графе.
     *
     * @param vertex Строковое значение вершины (имя).
     * @return Найденная вершина {@code Vertex}, {@code null} если вершина не найдена.
     */
    public Vertex findVertex(String vertex);

    /**
     * Метод возвращающий множество соседей вершины.
     *
     * @param vertex Строковое значение вершины (имя).
     * @return Множество соседних вершин.
     */
    public default Set<Vertex> getNeighbors(String vertex) {
        Vertex v = findVertex(vertex);
        if (v != null) {
            return Set.copyOf(v.getChildren());
        }
        throw new IllegalArgumentException("Вершина " + vertex + " отсутствует в графе!");
    }

    /**
     * Метод для считывания информации из файла и записи в граф.
     *
     * @param filename Имя файла для чтения
     */
    public default void readFromFile(String filename) {
        Path path = Path.of(filename);

        try {
            List<String> buffer = Files.readAllLines(path, StandardCharsets.UTF_8);

            int i = 0;
            int numberOfVertices = Integer.parseInt(buffer.get(i++));
            for (int j = 0; j < numberOfVertices; j++) {
                addVertex(buffer.get(i++));
            }

            int numberOfEdges = Integer.parseInt(buffer.get(i++));
            for (int j = 0; j < numberOfEdges; j++) {
                String[] edge = buffer.get(i++).trim().split(" ");
                addEdge(edge[0], edge[1]);
            }


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
