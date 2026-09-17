package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class MainTest {
    static Stream<int[]> argsProvideMethod() {
        return Stream.of(
                new int[] {},
                new int[] {1},
                new int[] {1, 2, 3, 4},
                new int[] {4, 3, 2, 1},
                new int[] {5, 6, 3, 4, 2, 7, 8, 3, 1, 6},
                new int[] {7, 7, 7, 7},
                new int[] {4, 5, -2, 0, -100, 324},
                new int[] {-100, 6, -4, 8, 3, 6, -4, 9, -4},
                new int[] {0, -10, Integer.MIN_VALUE, 5},
                new int[] {-3, 2, Integer.MAX_VALUE, -100},
                new int[] {5, -2, Integer.MAX_VALUE, 10, Integer.MIN_VALUE, -13}
        );
    }

    @ParameterizedTest
    @MethodSource("argsProvideMethod")
    @DisplayName("Сортировка различных массивов")
    void testSortArrayByHeapsort(int[] arr) {
        int[] arrCp = arr.clone();

        Main.heapsort(arr);
        Arrays.sort(arrCp);

        assertArrayEquals(arrCp, arr);
    }

    @Test
    @DisplayName("Сортировка случайного массива")
    void testSortRandomArrayByHeapsort() {
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

    @Test
    @DisplayName("Выброс NullPointerException при передаче null в функцию")
    void testNullArrayThrowsException() {
        assertThrows(NullPointerException.class, () -> Main.heapsort(null));
    }
}