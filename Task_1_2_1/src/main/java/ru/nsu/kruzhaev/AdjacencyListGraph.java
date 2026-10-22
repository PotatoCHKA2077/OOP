package ru.nsu.kruzhaev;

import java.util.*;

public class AdjacencyListGraph implements Graph{
    private Map<Vertex, Set<Vertex>> verticesList;

    public AdjacencyListGraph() {
        verticesList = new HashMap<>();
    }

    public AdjacencyListGraph(List<Vertex> vertices, List<Edge> edges) {
        verticesList = new HashMap<>();

        for (Vertex vrtx : vertices) {
            addVertex(vrtx);
        }

        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    public void addVertex(Vertex vertex) {
        verticesList.put(vertex, new HashSet<>());
    }

    public void removeVertex(Vertex vertex) {
        vertex.clean();
        verticesList.remove(vertex);
    }

    public void addEdge(Edge edge) {
        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.addChild(end);
        end.addParent(start);

        verticesList.get(start).add(end);
    }

    public void removeEdge(Edge edge) {
        Vertex start = edge.getStart();
        Vertex end = edge.getEnd();

        start.deleteChild(end);
        end.deleteParent(start);

        verticesList.get(start).remove(end);
    }

    public Set<Vertex> getVertices() {
        return verticesList.keySet();
    }

    public Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();

        for (Vertex vrtx : verticesList.keySet()) {
            for (Vertex v : verticesList.get(vrtx)) {
                edges.add(new Edge(vrtx, v));
            }
        }
        return edges;
    }

    public boolean hasVertex(String vertex) {
        return verticesList.containsKey(findVertex(vertex));
    }

    public boolean hasEdge(Edge edge) {
        return verticesList.containsKey(edge.getStart()) &&
                verticesList.get(edge.getStart()).contains(edge.getEnd());
    }

    private Vertex findVertex(String vertex) {
        for (Vertex vrtx : verticesList.keySet()) {
            if (Objects.equals(vrtx.getName(), vertex)) {
                return vrtx;
            }
        }
        return null;
    }
}
