package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class HashTableTest {

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
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Key other)) {
                return false;
            }
            return name.equals(other.name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Test
    void newTableIsEmpty() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals(0, table.getSize());
        assertEquals(0, table.getModCount());
        assertEquals(16, table.getTable().length);
    }

    @Test
    void putAddsNewEntryAndReturnsNull() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.put("one", 1));

        assertEquals(1, table.getSize());
        assertEquals(1, table.getModCount());
        assertEquals(1, table.get("one"));
        assertTrue(table.containsKey("one"));
    }

    @Test
    void putUpdatesExistingEntryAndReturnsOldValue() {
        HashTable<String, Integer> table = new HashTable<>();
        String key = "one";

        assertNull(table.put(key, 1));
        int oldModCount = table.getModCount();

        assertEquals(1, table.put(key, 10));

        assertEquals(1, table.getSize());
        assertEquals(10, table.get(key));
        assertEquals(oldModCount, table.getModCount());
    }

    @Test
    void putRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class, () -> table.put(null, 1));
    }

    @Test
    void putNotRejectsNullValue() {
        HashTable<String, Integer> table = new HashTable<>();

        assertDoesNotThrow(() -> table.put("key", null));
    }

    @Test
    void getRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class, () -> table.get(null));
    }

    @Test
    void containsKeyRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class, () -> table.containsKey(null));
    }

    @Test
    void removeRejectsNullKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NullPointerException.class, () -> table.remove(null, 1));
    }

    @Test
    void getReturnsNullForMissingKey() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.get("missing"));
        assertFalse(table.containsKey("missing"));
    }

    @Test
    void supportsNegativeHashCodes() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key key = new Key("negative", -1);

        table.put(key, 7);

        assertEquals(7, table.get(key));
        assertTrue(table.containsKey(key));
        assertNotNull(table.getTable()[15]);
    }

    @Test
    void storesSeveralKeysWithSameHashCode() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 5);
        Key second = new Key("second", 5);
        Key third = new Key("third", 5);

        table.put(first, 1);
        table.put(second, 2);
        table.put(third, 3);

        assertEquals(3, table.getSize());
        assertEquals(1, table.get(first));
        assertEquals(2, table.get(second));
        assertEquals(3, table.get(third));
        assertTrue(table.containsKey(first));
        assertTrue(table.containsKey(second));
        assertTrue(table.containsKey(third));
    }

    @Test
    void findsEqualButNotIdenticalKey() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key storedKey = new Key("same", 7);
        Key lookupKey = new Key("same", 7);

        table.put(storedKey, 70);

        assertEquals(70, table.get(lookupKey));
        assertTrue(table.containsKey(lookupKey));
    }

    @Test
    void removeReturnsFalseForEmptyTable() {
        HashTable<String, Integer> table = new HashTable<>();

        assertFalse(table.remove("missing", 1));
        assertEquals(0, table.getSize());
        assertEquals(0, table.getModCount());
    }

    @Test
    void removeDeletesHeadOfCollisionChain() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 4);
        Key second = new Key("second", 4);

        table.put(first, 1);
        table.put(second, 2);

        assertTrue(table.remove(second, 2));
        assertFalse(table.containsKey(second));
        assertTrue(table.containsKey(first));
        assertEquals(1, table.getSize());
    }

    @Test
    void removeDeletesMiddleOfCollisionChain() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 4);
        Key middle = new Key("middle", 4);
        Key last = new Key("last", 4);

        table.put(first, 1);
        table.put(middle, 2);
        table.put(last, 3);

        assertTrue(table.remove(middle, 2));
        assertFalse(table.containsKey(middle));
        assertTrue(table.containsKey(first));
        assertTrue(table.containsKey(last));
        assertEquals(2, table.getSize());
    }

    @Test
    void removeDeletesTailOfCollisionChain() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 4);
        Key second = new Key("second", 4);
        Key third = new Key("third", 4);

        table.put(first, 1);
        table.put(second, 2);
        table.put(third, 3);

        assertTrue(table.remove(first, 1));
        assertFalse(table.containsKey(first));
        assertTrue(table.containsKey(second));
        assertTrue(table.containsKey(third));
        assertEquals(2, table.getSize());
    }

    @Test
    void removeReturnsFalseWhenKeyIsNotExist() {
        HashTable<Key, Integer> table = new HashTable<>();
        table.put(new Key("stored", 6), 1);

        assertFalse(table.remove(new Key("missing", 6), 2));
        assertEquals(1, table.getSize());
    }

    @Test
    void removeReturnsFalseWhenValueDoesNotMatch() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key key = new Key("stored", 6);
        table.put(key, 1);

        assertFalse(table.remove(key, 999));
        assertEquals(1, table.getSize());
        assertEquals(1, table.get(key));
    }

    @Test
    void resizingDoublesCapacityAfterThirteenthEntry() {
        HashTable<Key, Integer> table = new HashTable<>();
        List<Key> keys = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            Key key = new Key("key" + i, i);
            keys.add(key);
            table.put(key, i);
        }

        assertEquals(32, table.getTable().length);
        assertEquals(13, table.getSize());

        for (int i = 0; i < keys.size(); i++) {
            assertEquals(i, table.get(keys.get(i)));
        }
    }

    @Test
    void resizingPreservesAllNodesInCollisionChains() {
        HashTable<Key, Integer> table = new HashTable<>();
        List<Key> keys = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            Key key = new Key("collision" + i, 2);
            keys.add(key);
            table.put(key, i);
        }

        assertEquals(32, table.getTable().length);
        assertEquals(13, table.getSize());

        for (int i = 0; i < keys.size(); i++) {
            assertEquals(i, table.get(keys.get(i)));
        }
    }

    @Test
    void toStringContainsEveryEntryAndBraces() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 1);
        Key second = new Key("second", 1);

        table.put(first, 10);
        table.put(second, 20);

        String result = table.toString();

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
        assertTrue(result.contains("first=10"));
        assertTrue(result.contains("second=20"));
    }

    @Test
    void emptyTableToStringIsEmptyBraces() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals("{}", table.toString());
    }

    @Test
    void hashCodeOfEmptyTableIsZero() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals(0, table.hashCode());
    }

    @Test
    void hashCodeIncludesAllEntriesInCollisionChain() {
        HashTable<Key, Integer> table = new HashTable<>();
        Key first = new Key("first", 3);
        Key second = new Key("second", 3);

        table.put(first, 10);
        table.put(second, 20);

        int expected = first.hashCode() + Integer.valueOf(10).hashCode()
                + second.hashCode() + Integer.valueOf(20).hashCode();

        assertEquals(expected, table.hashCode());
    }

    @Test
    void equalsReturnsFalseForNull() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNotEquals(null, table);
    }

    @Test
    void equalsReturnsFalseForDifferentType() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNotEquals("not a table", table);
    }

    @Test
    void equalsReturnsFalseForDifferentSizes() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();
        first.put("one", 1);
        second.put("one", 1);
        second.put("two", 2);

        assertNotEquals(first, second);
    }

    @Test
    void equalsReturnsTrueForTablesWithSameEntries() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();
        String one = "one";
        String two = "two";

        first.put(one, 1);
        first.put(two, 2);
        second.put(two, 2);
        second.put(one, 1);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void equalsReturnsFalseForDifferentValues() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();
        first.put("one", 1);
        second.put("one", 2);

        assertNotEquals(first, second);
    }
}