package ru.nsu.kruzhaev;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTableIterator<K, V> implements Iterator<HashNode<K, V>> {
    HashTable<K, V> table;
    int nodeIteratedNum;
    int currentHash;
    HashNode<K, V> currentNode;

    public HashTableIterator(HashTable<K, V> table) {
        this.table = table;
        nodeIteratedNum = 0;
        currentHash = 0;
        currentNode = null;
    }

    @Override
    public boolean hasNext() {
        return nodeIteratedNum < table.getSize();
    }

    @Override
    public HashNode<K, V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("В хеш таблице больше нет элементов!");
        }

        if (currentNode != null) {
            currentNode = currentNode.next;
        }
        while (currentNode == null) {
            currentNode = table.getTable()[++currentHash];
        }

        nodeIteratedNum++;
        return currentNode;
    }
}
