package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
Класс {@code Main}.
 */
public class Main {
    /**
     * Метод {@code main}.
     */
    static void main() {
        List<Vertex> vertices = new ArrayList<>(Arrays.asList(
                new Vertex("A"),
                new Vertex("B"),
                new Vertex("C")));

        List<Edge> edges = new ArrayList<>(Arrays.asList(
                new Edge("A", "B"),
                new Edge("B", "C"),
                new Edge("A", "C")
        ));

        Graph graph = new AdjacencyMatrixGraph(vertices, edges);
        System.out.println(graph.toString());

        Graph graph1 = new AdjacencyListGraph();

        graph1.addVertex("A");
        graph1.addVertex("B");
        graph1.addVertex("C");

        graph1.addEdge("A", "B");
        graph1.addEdge("A", "C");
        graph1.addEdge("B", "C");

        System.out.println(graph1.toString());

        Graph graph2 = new IncidenceMatrixGraph("graph.txt");
        System.out.println(graph2.toString());

        System.out.println(graph1.equals(graph2));
        System.out.println(graph1.equals(graph));
        System.out.println(graph.equals(graph2));

        TopologicalSort ts = new DfsSort(graph2);
        System.out.println(ts.getRes().toString());
    }
}