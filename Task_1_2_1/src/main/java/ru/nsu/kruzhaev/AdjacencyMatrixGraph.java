package ru.nsu.kruzhaev;

import java.util.*;

public class AdjacencyMatrixGraph implements Graph {
    private Map<Vertex, Map<Vertex, Boolean>> matrix;

    public AdjacencyMatrixGraph() {
        matrix = new HashMap<>();
    }

    public AdjacencyMatrixGraph(List<Vertex> vertices, List<Edge> edges) {
        matrix = new HashMap<>();

        for (Vertex vrtx : vertices) {
            addVertex(vrtx);
        }

        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    public void addVertex(Vertex vertex) {
        matrix.put(vertex, new HashMap<>());
        for (Vertex v : matrix.keySet()) {
            matrix.get(v).put(vertex, false);
        }
    }

    public void removeVertex(Vertex vertex) {
        vertex.clean();

        matrix.remove(vertex);
        for (Vertex vrtx : matrix.keySet()) {
            matrix.get(vrtx).remove(vertex);
        }
    }

    public void addEdge(Edge edge) {
        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.addChild(end);
        end.addParent(start);

        matrix.get(start).replace(end, true);
    }

    public void removeEdge(Edge edge) {
        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.deleteChild(end);
        end.deleteParent(start);

        matrix.get(edge.getStart()).replace(edge.getEnd(), false);
    }

    public Set<Vertex> getVertices() {
        return matrix.keySet();
    }

    public Set<Edge> getEdges() {
        Set<Edge> edgeList = new HashSet<>();
        for (Vertex vrtx : matrix.keySet()) {
            Map<Vertex, Boolean> haveEdges = matrix.get(vrtx);
            for (Vertex v : haveEdges.keySet()) {
                if (haveEdges.get(v)) {
                    edgeList.add(new Edge(vrtx, v));
                }
            }
        }
        return edgeList;
    }

    public boolean hasVertex(String vertex) {
        return matrix.containsKey(findVertex(vertex));
    }

    public boolean hasEdge(Edge edge) {
        return matrix.containsKey(edge.getStart())
                && matrix.get(edge.getStart()).containsKey(edge.getEnd());
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
