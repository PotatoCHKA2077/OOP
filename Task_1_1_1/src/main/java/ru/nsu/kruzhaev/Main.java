package ru.nsu.kruzhaev;

import java.util.Arrays;

/**
 * Класс Main.
 */
public class Main {
    /**
     * Функция сортировки массива кучей.
     *
     * @param arr массив.
     */
    public static void heapsort(int[] arr) {
        if (arr == null) {
            throw new NullPointerException();
        }
        Heap h = new Heap(arr);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = h.extractMin();
        }
    }

    /**
     * Функция входа в программу.
     *
     * @param args Параметры переданные при запуске.
     */
    public static void main(String[] args) {
        int[] arr = new int[] {5, 4, 8, 3, 2, 6};
        heapsort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
