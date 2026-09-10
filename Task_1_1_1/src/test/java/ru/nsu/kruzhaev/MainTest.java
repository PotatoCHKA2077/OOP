package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;


class MainTest {
    @Test
    void testHeapsort1() {
        int[] arr = new int[] {};
        int[] arrCp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arrCp);
        assertArrayEquals(arrCp, arr);
    }

    @Test
    void testHeapsort2() {
        int[] arr = new int[] {1};
        int[] arrCp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arrCp);
        assertArrayEquals(arrCp, arr);
    }

    @Test
    void testHeapsort3() {
        int[] arr = new int[] {1, 2, 3, 4};
        int[] arrCp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arrCp);
        assertArrayEquals(arrCp, arr);
    }

    @Test
    void testHeapsort4() {
        int[] arr = new int[] {4, 3, 2, 1};
        int[] arrCp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arrCp);
        assertArrayEquals(arrCp, arr);
    }

    @Test
    void testHeapsort5() {
        int[] arr = new int[] {5, 6, 3, 4, 2, 7, 8, 3, 1, 6};
        int[] arrCp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arrCp);
        assertArrayEquals(arrCp, arr);
    }

    @Test
    void testHeapsort6() {
        int[] arr = new int[10];
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            arr[i] = rand.nextInt(300);
        }
        int[] arrCp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arrCp);
        assertArrayEquals(arrCp, arr);
    }
}