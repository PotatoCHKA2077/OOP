package ru.nsu.kruzhaev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    @Test
    void stringConstructorTest() {
        Edge edge = new Edge("A", "B");
        assertEquals("A", edge.getStart().getName());
        assertEquals("B", edge.getEnd().getName());
    }

    @Test
    void vertexConstructorTest() {
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Edge edge = new Edge(a, b);

        assertSame(a, edge.getStart());
        assertSame(b, edge.getEnd());
    }

    @Test
    void equalityAndHashCodeTest() {
        Edge ab1 = new Edge("A", "B");
        Edge ab2 = new Edge(new Vertex("A"), new Vertex("B"));
        Edge ba = new Edge("B", "A");

        assertEquals(ab1, ab2);
        assertEquals(ab1.hashCode(), ab2.hashCode());
        assertNotEquals(ab1, ba);
        assertNotEquals(null, ab1);
        assertNotEquals("A -> B", ab1);
    }

    @Test
    void toStringReturnsTest() {
        assertEquals("A -> B", new Edge("A", "B").toString());
    }
}
