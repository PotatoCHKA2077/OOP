package ru.nsu.kruzhaev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashNodeTest {

    @Test
    void constructorStoresKeyAndValueAndStartsWithoutNext() {
        HashNode<String, Integer> node = new HashNode<>("key", 10);

        assertEquals("key", node.getKey());
        assertEquals(10, node.getValue());
        assertNull(node.getNext());
    }

    @Test
    void constructorRejectsNullKey() {
        assertThrows(NullPointerException.class,
                () -> new HashNode<>(null, 10));
    }

    @Test
    void settersChangeValueAndNext() {
        HashNode<String, Integer> node = new HashNode<>("first", 1);
        HashNode<String, Integer> next = new HashNode<>("second", 2);

        node.setValue(100);
        node.setNext(next);

        assertEquals(100, node.getValue());
        assertSame(next, node.getNext());
    }

    @Test
    void setterAllowsNullValue() {
        HashNode<String, Integer> node = new HashNode<>("key", 1);

        node.setValue(null);

        assertNull(node.getValue());
        assertEquals("key=null", node.toString());
    }

    @Test
    void setterAllowsNullNext() {
        HashNode<String, Integer> node = new HashNode<>("key", 1);
        node.setNext(new HashNode<>("next", 2));

        node.setNext(null);

        assertNull(node.getNext());
    }

    @Test
    void toStringPrintsKeyAndValue() {
        HashNode<String, Integer> node = new HashNode<>("answer", 42);

        assertEquals("answer=42", node.toString());
    }

    @Test
    void toStringPrintsNullValue() {
        HashNode<String, Integer> node = new HashNode<>("answer", null);

        assertEquals("answer=null", node.toString());
    }

    @Test
    void hashCodeIsSumOfKeyAndValueHashCodes() {
        String key = "key";
        Integer value = 123;
        HashNode<String, Integer> node = new HashNode<>(key, value);

        assertEquals(key.hashCode() + value.hashCode(), node.hashCode());
    }

    @Test
    void equalsReturnsTrueForSameObject() {
        HashNode<String, Integer> node = new HashNode<>("key", 1);

        assertEquals(node, node);
    }

    @Test
    void equalsReturnsFalseForNull() {
        HashNode<String, Integer> node = new HashNode<>("key", 1);

        assertNotEquals(null, node);
    }

    @Test
    void equalsReturnsFalseForObjectOfAnotherType() {
        HashNode<String, Integer> node = new HashNode<>("key", 1);

        assertNotEquals("key=1", node);
    }

    @Test
    void equalsReturnsTrueForDifferentNodesWithSameContent() {
        HashNode<String, Integer> first = new HashNode<>("key", 1);
        HashNode<String, Integer> second = new HashNode<>("key", 1);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void equalsReturnsFalseWhenKeysDiffer() {
        HashNode<String, Integer> first = new HashNode<>("first", 1);
        HashNode<String, Integer> second = new HashNode<>("other", 1);

        assertNotEquals(first, second);
    }

    @Test
    void equalsReturnsFalseWhenValuesDiffer() {
        HashNode<String, Integer> first = new HashNode<>("key", 1);
        HashNode<String, Integer> second = new HashNode<>("key", 2);

        assertNotEquals(first, second);
    }

    @Test
    void equalsComparesNextNodes() {
        HashNode<String, Integer> first = new HashNode<>("head", 1);
        HashNode<String, Integer> second = new HashNode<>("head", 1);
        first.setNext(new HashNode<>("tail", 2));
        second.setNext(new HashNode<>("tail", 2));

        assertEquals(first, second);
    }

    @Test
    void hashCodeHandlesNullValue() {
        HashNode<String, Integer> node = new HashNode<>("key", null);

        assertDoesNotThrow(node::hashCode);
    }
}
