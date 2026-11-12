package ru.nsu.kruzhaev;

import java.util.Iterator;

public class HashTable<K, V>  implements Iterable<HashNode<K, V>> {

    private HashNode<K, V>[] table;
    private int size;
    private int capacity;
    private int modCount;
    private static final float LOAD_FACTOR = 0.75f;

    public HashTable() {
        size = 0;
        capacity = 16;
        modCount = 0;
        table = new HashNode[capacity];
    }

    private int hash(K key) {
        int res = key.hashCode() % capacity;
        if (res < 0) {
            res += capacity;
        }

        return res;
    }

    private HashNode<K, V> findNode(K key) {
        if (key == null) {
            throw new NullPointerException();
        }

        int index = hash(key);

        HashNode<K, V> node = table[index];

        while (node != null) {
            if (node.getKey().equals(key)) {
                return node;
            }

            node = node.getNext();
        }

        return null;
    }

    private void checkLoading() {
        if ((float) size / capacity > LOAD_FACTOR) {
            HashNode<K, V>[] cpTable = table;

            capacity *= 2;
            table = new HashNode[capacity];

            for (HashNode<K, V> node : cpTable) {
                while (node != null) {
                    put(node.getKey(), node.getValue());
                    size--;
                    modCount--;
                    node = node.getNext();
                }
            }
        }
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        int index = hash(key);
        HashNode<K, V> node = table[index];

        while (node != null) {
            if (node.getKey().equals(key)) {
                V old = node.getValue();
                node.setValue(value);
                return old;
            }

            node = node.getNext();
        }

        node = new HashNode<>(key, value);
        node.setNext(table[index]);
        table[index] = node;
        size++;
        modCount++;
        checkLoading();
        return null;
    }

    public boolean remove(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        int index = hash(key);
        HashNode<K, V> node = table[index];

        if (node == null) {
            return false;
        }

        if (node.getKey().equals(key) && node.getValue().equals(value)) {
            table[index] = node.getNext();
            size--;
            modCount++;
            return true;
        }

        while (node.getNext() != null) {
            HashNode<K, V> next = node.getNext();
            if (next.getKey().equals(key) && next.getValue().equals(value)) {
                node.setNext(next.getNext());
                size--;
                modCount++;
                return true;
            }
            node = next;
        }

        return false;
    }

    public V get(K key) {
        HashNode<K, V> node = findNode(key);

        if (node != null) {
            return node.getValue();
        }
        return null;
    }

    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    public int getSize() {
        return size;
    }

    public int getModCount(){
        return modCount;
    }

    public HashNode<K, V>[] getTable() {
        return table;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public Iterator<HashNode<K, V>> iterator() {
        return new HashTableIterator<>(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        boolean first = true;

        for (HashNode<K, V> node : table) {
            while (node != null) {
                if (!first) {
                    stringBuilder.append(", ");
                }

                first = false;
                stringBuilder.append(node.toString());
                node = node.getNext();
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int res = 0;

        for (HashNode<K, V> node : table) {
            while (node != null) {
                res += node.hashCode();
                node = node.getNext();
            }
        }

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable)) {
            return false;
        }

        HashTable<K, V> t = (HashTable<K, V>) o;

        if (size != t.getSize() || hashCode() != o.hashCode()) {
            return false;
        }

        for (HashNode<K, V> n : table) {
            while (n != null) {
                if (!t.containsKey(n.getKey())) {
                    return false;
                }
                if (t.get(n.getKey()) != n.getValue()) {
                    return false;
                }
                n = n.getNext();
            }
        }

        return true;
    }
}
