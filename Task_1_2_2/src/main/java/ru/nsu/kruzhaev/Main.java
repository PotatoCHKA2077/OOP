package ru.nsu.kruzhaev;

/**
 * Клас Main.
 */
public class Main {
    /**
     * Функция main.
     */
    static void main() {
        HashTable<Integer, String> htable = new HashTable<>();

        htable.put(-3, "aboba");
        htable.put(150, "jdjdjd");

        System.out.println(htable.toString());
    }
}
