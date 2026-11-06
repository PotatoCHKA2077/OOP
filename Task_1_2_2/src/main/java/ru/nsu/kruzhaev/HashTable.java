package ru.nsu.kruzhaev;

import java.util.Iterator;

public class HashTable<K, V>  implements Iterable<HashNode<K, V>>{

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

    private HashNode<K, V> findPrevious(K key) { // думаю переделать на просто поиск, а не предыдущего, но потом
        if (key == null) {
            throw new NullPointerException();
        }

        int index = hash(key);

        HashNode<K, V> node = table[index];

        if (node == null) {
            return null;
        }

        while (node.next != null) {
            if (node.next.key.equals(key)) {
                return node;
            }

            node = node.next;
        }

        return node;
    }

    private void checkLoading() {
        if ((float) size / capacity > LOAD_FACTOR) {
            HashNode<K, V>[] cpTable = table.clone();

            capacity *= 2;
            table = new HashNode[capacity];

            for (HashNode<K, V> node : this) {
                put(node.key, node.value);
            }
        }
    }

    public V put(K key, V value) {
        HashNode<K, V> node = findPrevious(key);

        if (node == null) {
            node = new HashNode<>(key, value);
            table[hash(key)] = node;
            size++;
            modCount++;
            checkLoading();
        } else if (node.next != null) {
            node = node.next;
            V old = node.value;
            node.value = value;
            modCount++;
            return old;
        } else if (node.key.equals(key)) {
            V old = node.value;
            node.value = value;
            modCount++;
            return old;
        } else {
            node.next = new HashNode<>(key, value);
            size++;
            modCount++;
            checkLoading();
        }
        return null;
    }

    public boolean remove(K key, V value) {
        HashNode<K, V> node = findPrevious(key);

        if (node != null) {
            if (node.key.equals(key) && node.value.equals(value)) {
                table[hash(key)] = node.next;
                size--;
                modCount++;
                return true;
            } else {
                node = node.next;
                if (node != null && node.key.equals(key) && node.value.equals(value)) {
                    if (node.next != null) {
                        node.next = node.next.next;
                    }
                    size--;
                    modCount++;
                    return true;
                }
            }
        }

        return false;
    }

    public V get(K key) {
        HashNode<K, V> node = findPrevious(key);

        if (node == null) {
            return null;
        } else if (node.key.equals(key)) {
            return node.value;
        } else if (node.next.key.equals(key)) {
            return node.next.value;
        }
        return null;
    }

    public boolean containsKey(K key) {
        HashNode<K, V> node = findPrevious(key);

        if (node == null) {
            return false;
        } else if (node.key.equals(key)) {
            return true;
        } else {
            return node.next.key.equals(key);
        }
    }

    public int getSize() {
        return size;
    }

    public HashNode<K, V>[] getTable() {
        return table;
    }

    @Override
    public Iterator<HashNode<K, V>> iterator() {
        return new HashTableIterator<>(this);
    }
}
