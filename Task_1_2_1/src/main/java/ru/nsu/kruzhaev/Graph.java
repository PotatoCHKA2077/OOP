package ru.nsu.kruzhaev;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface Graph {

    public void addVertex(Vertex vertex);

    public void removeVertex(Vertex vertex);

    public void addEdge(Edge edge);

    public void removeEdge(Edge edge);

    public Set<Vertex> getVertices();

    public Set<Edge> getEdges();

    public boolean hasVertex(String vertex);

    public boolean hasEdge(Edge edge);

    private Vertex findVertex(String vertex) {
        return null;
    }

    public default Set<Vertex> getNeighbors(String vertex) {
        Vertex vrtx = findVertex(vertex);
        return Set.copyOf(vrtx.getChildren());
    }

    public default void readFromFile(String filename) {
        Path path = Path.of(filename);

        try {
            List<String> buffer = Files.readAllLines(path, StandardCharsets.UTF_8);

            int i = 0;
            int numberOfVertices = Integer.parseInt(buffer.get(i++));
            for (int j = 0; j < numberOfVertices; j++) {
                addVertex(new Vertex(buffer.get(i++)));
            }

            int numberOfEdges = Integer.parseInt(buffer.get(i++));
            for (int j = 0; j < numberOfEdges; j++) {
                String[] edge = buffer.get(i++).trim().split(" ");
                addEdge(new Edge(
                        findVertex(edge[0]),
                        findVertex(edge[1])));
            }


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public default boolean equals(Graph graph) {
        return Objects.equals(getVertices(), graph.getVertices())
                && Objects.equals(getEdges(), graph.getEdges());
    }

    public default String graphToString() {
        return "Vertices: " + getVertices().toString()
                + "\nEdges: " + getEdges().toString();
    }
}
