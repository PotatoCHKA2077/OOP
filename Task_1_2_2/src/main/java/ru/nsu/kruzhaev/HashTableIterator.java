package ru.nsu.kruzhaev;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTableIterator<K, V> implements Iterator<HashNode<K, V>> {
    HashTable<K, V> table;
    int nodeIteratedNum;
    int currentHash;
    HashNode<K, V> currentNode;
    int expectedModCount;

    public HashTableIterator(HashTable<K, V> table) {
        this.table = table;
        nodeIteratedNum = 0;
        currentHash = 0;
        currentNode = null;
        expectedModCount = table.getModCount();
    }

    @Override
    public boolean hasNext() {
        if (table.getModCount() != expectedModCount) {
            throw new ConcurrentModificationException();
        }

        if (currentNode != null && currentNode.getNext() != null) {
            return true;
        }
        return nodeIteratedNum < table.getSize();
    }

    @Override
    public HashNode<K, V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("В хеш таблице больше нет элементов!");
        }

        if (currentNode != null && currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
            nodeIteratedNum++;
            return currentNode;
        }

        if (currentNode != null) {
            currentHash++;
        }

        while (table.getTable()[currentHash] == null) {
            currentHash++;
            if (currentHash >= table.getCapacity()) {
                throw new NoSuchElementException();
            }
        }

        currentNode = table.getTable()[currentHash];
        nodeIteratedNum++;
        return currentNode;
    }
}
