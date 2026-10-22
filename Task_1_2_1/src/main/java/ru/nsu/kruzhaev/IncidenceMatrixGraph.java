package ru.nsu.kruzhaev;

import java.util.*;

public class IncidenceMatrixGraph implements Graph{
    private Map<Vertex, Map<Edge, Integer>> matrix;
    private Set<Edge> edges;

    public IncidenceMatrixGraph() {
        matrix = new HashMap<>();
        this.edges = new HashSet<>();
    }

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

    public void addVertex(Vertex vertex) {
        matrix.put(vertex, new HashMap<>());
        for (Edge edge : edges) {
            matrix.get(vertex).put(edge, 0);
        }
    }

    public void removeVertex(Vertex vertex) {
        vertex.clean();

        matrix.remove(vertex);
        for (Map<Edge, Integer> e : matrix.values()) {
            for (Edge edge : edges) {
                if (Objects.equals(edge.getStart(), vertex) ||
                        Objects.equals(edge.getEnd(), vertex)) {
                    e.remove(edge);
                }
            }
        }
    }

    public void addEdge(Edge edge) {
        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.addChild(end);
        end.addParent(start);

        matrix.get(start).put(edge, -1);
        matrix.get(end).put(edge, 1);
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.deleteChild(end);
        end.deleteParent(start);

        for (Vertex vrtx : matrix.keySet()) {
            matrix.get(vrtx).remove(edge);
        }
    }

    public Set<Vertex> getVertices() {
        return matrix.keySet();
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public boolean hasVertex(String vertex) {
        return matrix.containsKey(findVertex(vertex));
    }

    public boolean hasEdge(Edge edge) {
        return edges.contains(edge);
    }

    private Vertex findVertex(String vertex) {
        for (Vertex vrtx : matrix.keySet()) {
            if (Objects.equals(vrtx.getName(), vertex)) {
                return vrtx;
            }
        }
        return null;
    }
}
