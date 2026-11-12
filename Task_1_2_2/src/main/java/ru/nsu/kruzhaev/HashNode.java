package ru.nsu.kruzhaev;

/**
 * Класс узла хеш-таблицы.
 *
 * @param <K> Тип ключа.
 * @param <V> Тип значения.
 */
public class HashNode<K, V> {
    private final K key;
    private V value;
    private HashNode<K, V> next;

    /**
     * Конструктор класса.
     *
     * @param key ключ нового узла.
     * @param value значение нового узла.
     */
    public HashNode(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        this.key = key;
        this.value = value;
        next = null;
    }

    /**
     * Метод возвращает ключ узла.
     *
     * @return ключ узла.
     */
    public K getKey() {
        return key;
    }

    /**
     * Метод возвращает значение узла.
     *
     * @return значение узла.
     */
    public V getValue() {
        return value;
    }

    /**
     * Метод возвращает следующий узел.
     *
     * @return следующий узел.
     */
    public HashNode<K, V> getNext() {
        return next;
    }

    /**
     * Метод изменяет значение узла.
     *
     * @value новое значение узла.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Метод изменяет следующий узел.
     *
     * @value новый следующий узел.
     */
    public void setNext(HashNode<K, V> next) {
        this.next = next;
    }

    /**
     * Возвращает строковое представление узла.
     *
     * @return строковое представление узла.
     */
    @Override
    public String toString() {
        if (value == null) {
            return key.toString() + "=null";
        }
        return key.toString() + "=" + value.toString();
    }

    /**
     * Вычисляет хеш-код узла.
     *
     * @return хеш-код узла.
     */
    @Override
    public int hashCode() {
        int res = 0;
        res += key.hashCode();

        if (value != null) {
            res += value.hashCode();
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
        if (!(o instanceof HashNode) || o.hashCode() != this.hashCode()) {
            return false;
        }

        HashNode<K, V> node = (HashNode<K, V>) o;

        return key.equals(node.key) && value.equals(node.value)
                && (next == null && node.next == null || next != null && next.equals(node.next));
    }
}
