package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VertexTest {
    @Test
    void constructorTest() {
        Vertex v = new Vertex("A");
        assertEquals("A", v.getName());
        assertTrue(v.getParents().isEmpty());
        assertTrue(v.getChildren().isEmpty());
    }

    @Test
    void managesParentsAndChildrenTest() {
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");

        a.addChild(b);
        b.addParent(a);
        assertTrue(a.getChildren().contains(b));
        assertTrue(b.getParents().contains(a));

        a.deleteChild(b);
        b.deleteParent(a);
        assertFalse(a.getChildren().contains(b));
        assertFalse(b.getParents().contains(a));
    }

    @Test
    void cleanTest() {
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        a.addChild(b);
        b.addParent(a);
        b.addChild(c);
        c.addParent(b);

        b.clean();

        assertFalse(a.getChildren().contains(b));
        assertFalse(c.getParents().contains(b));
    }

    @Test
    void equalityAndHashCodeTest() {
        Vertex a1 = new Vertex("A");
        Vertex a2 = new Vertex("A");
        Vertex b = new Vertex("B");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, b);
    }

    @Test
    void nullNameTest() {
        assertThrows(NullPointerException.class, () -> new Vertex(null));
    }

    @Test
    void toStringReturnsNameTest() {
        assertEquals("Start", new Vertex("Start").toString());
    }
}
