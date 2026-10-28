package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;

class GraphTest {
    @TempDir Path tempDir;

    private List<Graph> newGraphs() {
        return List.of(
            new AdjacencyListGraph(),
            new AdjacencyMatrixGraph(),
            new IncidenceMatrixGraph()
        );
    }

    @Test
    void addsFindsAndRemovesVerticesTest() {
        for (Graph graph : newGraphs()) {
            graph.addVertex("A");
            graph.addVertex("B");

            assertTrue(graph.hasVertex("A"));
            assertTrue(graph.hasVertex("B"));
            assertEquals(new Vertex("A"), graph.findVertex("A"));

            assertThrows(IllegalArgumentException.class, () -> graph.addVertex("A"));
            graph.removeVertex("B");
            assertFalse(graph.hasVertex("B"));
            assertEquals(Set.of(new Vertex("A")), graph.getVertices());
            assertThrows(IllegalArgumentException.class, () -> graph.removeVertex("B"));
        }
    }

    @Test
    void addsGetsAndRemovesEdgesTest() {
        for (Graph graph : newGraphs()) {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");

            graph.addEdge("A", "B");
            graph.addEdge("A", "C");

            assertTrue(graph.hasEdge("A", "B"));
            assertTrue(graph.hasEdge(new Edge("A", "C")));
            assertEquals(Set.of(new Edge("A", "B"), new Edge("A", "C")), graph.getEdges());
            assertEquals(Set.of(new Vertex("B"), new Vertex("C")), graph.getNeighbors("A"));

            assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "B"));

            graph.removeEdge("A", "B");
            assertFalse(graph.hasEdge("A", "B"));
            assertEquals(Set.of(new Edge("A", "C")), graph.getEdges());
            assertThrows(IllegalArgumentException.class,
                    () -> graph.removeEdge(new Edge("A", "B")));
        }
    }

    @Test
    void removingVertexAlsoRemovesIncidentEdgesTest() {
        for (Graph graph : newGraphs()) {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addEdge("A", "B");
            graph.addEdge("B", "C");

            graph.removeVertex("B");

            assertFalse(graph.hasVertex("B"));
            assertTrue(graph.getEdges().isEmpty());
            assertTrue(graph.getNeighbors("A").isEmpty());
            assertTrue(graph.getNeighbors("C").isEmpty());
        }
    }

    @Test
    void missingVertexTest() {
        for (Graph graph : newGraphs()) {
            assertNull(graph.findVertex("missing"));
            assertFalse(graph.hasVertex("missing"));
            assertThrows(IllegalArgumentException.class, () -> graph.getNeighbors("missing"));
            graph.addVertex("A");
            assertThrows(NullPointerException.class, () -> graph.addEdge("A", "missing"));
        }
    }

    @Test
    void sameGraphsComparingTest() {
        Graph list = new AdjacencyListGraph();
        Graph matrix = new AdjacencyMatrixGraph();
        Graph incidence = new IncidenceMatrixGraph();

        for (Graph graph : List.of(list, matrix, incidence)) {
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addEdge("A", "B");
            graph.addEdge("B", "C");
        }

        assertEquals(list, matrix);
        assertEquals(matrix, incidence);
        assertEquals(list.hashCode(), matrix.hashCode());
        assertEquals(matrix.hashCode(), incidence.hashCode());
        assertNotEquals(new AdjacencyListGraph(), list);
        assertTrue(list.toString().contains("Vertices:"));
        assertTrue(list.toString().contains("Edges:"));
    }

    @Test
    void readsGraphFromFileTest() throws Exception {
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, """
            3
            A
            B
            C
            2
            A B
            B C
            """);

        for (Graph graph : List.of(
                new AdjacencyListGraph(file.toString()),
                new AdjacencyMatrixGraph(file.toString()),
                new IncidenceMatrixGraph(file.toString()))) {
            assertEquals(3, graph.getVertices().size());
            assertEquals(2, graph.getEdges().size());
            assertTrue(graph.hasEdge("A", "B"));
            assertTrue(graph.hasEdge("B", "C"));
        }
    }

    @Test
    void defaultAddAndRemoveTest() {
        for (Graph graph : newGraphs()) {
            graph.addVertex(new Vertex("A"));
            graph.addVertex(new Vertex("B"));
            graph.addEdge("A", "B");
            assertTrue(graph.hasEdge("A", "B"));
            graph.removeEdge("A", "B");
            assertFalse(graph.hasEdge("A", "B"));
            graph.removeVertex("B");
            assertFalse(graph.hasVertex("B"));
        }
    }
}
