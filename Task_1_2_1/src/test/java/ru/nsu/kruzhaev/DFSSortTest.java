package ru.nsu.kruzhaev;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DFSSortTest {
    private Graph makeGraph() {
        Graph graph = new AdjacencyListGraph();
        for (String name : List.of("A", "B", "C", "D")) graph.addVertex(name);
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        return graph;
    }

    @Test
    void sortsInTopologicalOrderTest() {
        Graph graph = makeGraph();
        List<Vertex> order = new DFSSort(graph).getRes();

        assertEquals(graph.getVertices().size(), order.size());
        assertEquals(graph.getVertices(), new HashSet<>(order));
        for (Edge edge : graph.getEdges()) {
            assertTrue(order.indexOf(edge.getStart()) < order.indexOf(edge.getEnd()));
        }
    }

    @Test
    void detectsCycleTest() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "B");

        assertThrows(RuntimeException.class, () -> new DFSSort(graph));
    }

    @Test
    void sortsSingleVertexGraphTest() {
        Graph graph = new AdjacencyListGraph();
        graph.addVertex("A");
        assertEquals(List.of(new Vertex("A")), new DFSSort(graph).getRes());
    }
}
