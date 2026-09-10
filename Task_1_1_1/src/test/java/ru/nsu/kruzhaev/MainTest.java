package ru.nsu.kruzhaev;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void testHeapsort1() {
        int[] arr = new int[] {};
        int[] arr_cp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arr_cp);
        assertArrayEquals(arr_cp, arr);
    }

    @Test
    void testHeapsort2() {
        int[] arr = new int[] {1};
        int[] arr_cp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arr_cp);
        assertArrayEquals(arr_cp, arr);
    }

    @Test
    void testHeapsort3() {
        int[] arr = new int[] {1, 2, 3, 4};
        int[] arr_cp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arr_cp);
        assertArrayEquals(arr_cp, arr);
    }

    @Test
    void testHeapsort4() {
        int[] arr = new int[] {4, 3, 2, 1};
        int[] arr_cp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arr_cp);
        assertArrayEquals(arr_cp, arr);
    }

    @Test
    void testHeapsort5() {
        int[] arr = new int[] {5, 6, 3, 4, 2, 7, 8, 3, 1, 6};
        int[] arr_cp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arr_cp);
        assertArrayEquals(arr_cp, arr);
    }

    @Test
    void testHeapsort6() {
        int[] arr = new int[10];
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            arr[i] = rand.nextInt(300);
        }
        int[] arr_cp = arr.clone();
        Main.heapsort(arr);
        Arrays.sort(arr_cp);
        assertArrayEquals(arr_cp, arr);
    }
}