package ru.nsu.kruzhaev;

import java.util.Iterator;

/**
 * Класс {@code HashTable} хеш-таблицы.
 *
 * @param <K> Тип ключей.
 * @param <V> Тип значений.
 */
public class HashTable<K, V>  implements Iterable<HashNode<K, V>> {
    private HashNode<K, V>[] table;
    private int size;
    private int capacity;
    private int modCount;
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * Конструктор класса {@code HashTable}.
     */
    public HashTable() {
        size = 0;
        capacity = 16;
        modCount = 0;
        table = new HashNode[capacity];
    }

    /**
     * Метод вычисляющий хеш ключа.
     *
     * @param key Ключ для вычисления хеша.
     * @return Хеш ключа.
     */
    private int hash(K key) {
        int res = key.hashCode() % capacity;
        if (res < 0) {
            res += capacity;
        }

        return res;
    }

    /**
     * Метод ищущий узел хеш-таблицы по ключу.
     *
     * @param key Ключ для поиска.
     * @return Узел с заданным ключом, если есть в таблице, если нет {@code null}.
     */
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

    /**
     * Метод проверяющий заполненность хеш-таблицы. Увеличивает размер таблицы по необходимости.
     */
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

    /**
     * Метод для добавления узла в хеш-таблицу
     *
     * @param key Ключ узла.
     * @param value Значение узла.
     * @return Если элемент с таким ключом уже есть в хеш-таблице метод возвращает старое значение,
     * иначе {@code null}.
     */
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

    /**
     * Метод для удаления узла из хеш-таблицы.
     *
     * @param key Ключ узла.
     * @param value Значение узла.
     * @return {@code true} если операция удалась, иначе {@code false}.
     */
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

    /**
     * Метод для поиска значения по ключу.
     *
     * @param key Ключ для поиска.
     * @return {@code value} значение узла если он есть в хеш-таблице, иначе {@code null}.
     */
    public V get(K key) {
        HashNode<K, V> node = findNode(key);

        if (node != null) {
            return node.getValue();
        }
        return null;
    }

    /**
     * Метод для проверки нахождения узла по ключу.
     *
     * @param key Ключ для поиска.
     * @return {@code true} если узел есть в хеш-таблице, иначе {@code false}.
     */
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    /**
     * Возвращает текущее количество узлов в хеш-таблице.
     *
     * @return Текущее количество узлов в хеш-таблице.
     */
    public int getSize() {
        return size;
    }

    /**
     * Возвращает текущее количество изменений в хеш-таблице.
     *
     * @return Текущее количество изменений в хеш-таблице.
     */
    public int getModCount(){
        return modCount;
    }

    /**
     * Возвращает таблицу с узлами.
     *
     * @return Таблица с узлами.
     */
    public HashNode<K, V>[] getTable() {
        return table;
    }

    /**
     * Возвращает текущее количество хешей в хеш-таблице.
     *
     * @return Текущее количество хешей в хеш-таблице.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Создает итератор по хеш-таблице.
     *
     * @return Итератор.
     */
    @Override
    public Iterator<HashNode<K, V>> iterator() {
        return new HashTableIterator<>(this);
    }

    /**
     * Возвращает строковое представление хеш-таблицы.
     *
     * @return строковое представление хеш-таблицы.
     */
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

    /**
     * Вычисляет хеш-код таблицы.
     *
     * @return хеш-код таблицы.
     */
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

    /**
     * Проверяет объекты на совпадение.
     *
     * @param o   the reference object with which to compare.
     * @return {@code true} если объекты совпадают, иначе {@code false}.
     */
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
