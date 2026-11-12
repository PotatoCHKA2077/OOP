package ru.nsu.kruzhaev;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HashTableIteratorTest {

    private static final class Key {
        private final String name;
        private final int hash;

        private Key(String name, int hash) {
            this.name = name;
            this.hash = hash;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Test
    void iteratorOfEmptyTableHasNoNextElement() {
        HashTable<String, Integer> table = new HashTable<>();
        Iterator<HashNode<String, Integer>> iterator = table.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    void nextOnEmptyIteratorThrowsNoSuchElementException() {
        HashTable<String, Integer> table = new HashTable<>();
        Iterator<HashNode<String, Integer>> iterator = table.iterator();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratesSingleEntry() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key key = new Key("one", 1);
        table.put(key, 10);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();

        assertTrue(iterator.hasNext());
        HashNode<Key, Integer> node = iterator.next();
        assertSame(key, node.getKey());
        assertEquals(10, node.getValue());
        assertFalse(iterator.hasNext());
    }

    @Test
    void repeatedHasNextDoesNotSkipElement() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key key = new Key("one", 1);
        table.put(key, 10);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();

        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertSame(key, iterator.next().getKey());
    }

    @Test
    void iteratesEntriesInDifferentBuckets() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 1);
        Key second = new Key("second", 3);
        Key third = new Key("third", 5);

        table.put(first, 10);
        table.put(second, 20);
        table.put(third, 30);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();
        Set<Key> visited = new HashSet<>();

        while (iterator.hasNext()) {
            visited.add(iterator.next().getKey());
        }

        assertEquals(Set.of(first, second, third), visited);
    }

    @Test
    void visitsEntryStoredInBucketZero() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key key = new Key("zero", 0);
        table.put(key, 0);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(key, iterator.next().getKey());
    }

    @Test
    void iteratesEveryNodeInCollisionChain() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 2);
        Key second = new Key("second", 2);
        Key third = new Key("third", 2);

        table.put(first, 1);
        table.put(second, 2);
        table.put(third, 3);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();
        Set<Key> visited = new HashSet<>();

        while (iterator.hasNext()) {
            visited.add(iterator.next().getKey());
        }

        assertEquals(Set.of(first, second, third), visited);
        assertEquals(3, visited.size());
    }

    @Test
    void nextAfterLastElementThrowsNoSuchElementException() {
        HashTable<Key, Integer> table = new HashTable<>();
        table.put(new Key("one", 1), 1);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();
        iterator.next();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorDetectsInsertionAfterCreation() {
        HashTable<Key, Integer> table = new HashTable<>();
        table.put(new Key("one", 1), 1);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();
        table.put(new Key("two", 2), 2);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void iteratorDetectsRemovalAfterCreation() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key key = new Key("one", 1);
        table.put(key, 1);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();
        table.remove(key, 1);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void iteratorDetectsModificationWhenCallingNext() {
        HashTable<Key, Integer> table = new HashTable<>();
        table.put(new Key("one", 1), 1);

        Iterator<HashNode<Key, Integer>> iterator = table.iterator();
        table.put(new Key("two", 2), 2);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}
