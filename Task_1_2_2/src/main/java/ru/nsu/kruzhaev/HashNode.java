package ru.nsu.kruzhaev;

public class HashNode<K, V> {
    private final K key;
    private V value;
    private HashNode<K, V> next;

    public HashNode(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        this.key = key;
        this.value = value;
        next = null;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public HashNode<K, V> getNext() {
        return next;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setNext(HashNode<K, V> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        if (value == null) {
            return key.toString() + "=null";
        }
        return key.toString() + "=" + value.toString();
    }

    @Override
    public int hashCode() {
        int res = 0;
        res += key.hashCode();

        if (value != null) {
            res += value.hashCode();
        }

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashNode) || o.hashCode() != this.hashCode()) {
            return false;
        }

        HashNode<K, V> node = (HashNode<K, V>) o;

        return key.equals(node.key) && value.equals(node.value)
                && (next == null && node.next == null || next != null && next.equals(node.next));
    }
}
