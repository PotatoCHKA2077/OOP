package ru.nsu.kruzhaev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {
    static Stream<int[]> argsProvideMethod(){
        return Stream.of(
                new int[] {},
                new int[] {1},
                new int[] {1, 2, 3, 4},
                new int[] {4, 3, 2, 1},
                new int[] {5, 6, 3, 4, 2, 7, 8, 3, 1, 6},
                new int[] {4, 5, -2, 6, -100, 324},
                new int[] {-100, 6, -4, 8, 3, 6, -4, 9, -4},
                new int[] {0, -10, Integer.MIN_VALUE, 5},
                new int[] {-3, 2, Integer.MAX_VALUE, -100}
        );
    }

    @ParameterizedTest
    @MethodSource("argsProvideMethod")
    @DisplayName("Вывод элементов в отсортированном порядке")
    void testExtractMinUnsortedArray(int[] arr) {
        Heap heap = new Heap(arr);

        Arrays.sort(arr);

        for(int elem: arr){
            assertEquals(elem, heap.extractMin());
        }
    }

    @Test
    @DisplayName("Выброс IllegalStateException при попытке извлечения из пустой кучи")
    void testExtractMinFromEmptyHeapThrowsException() {
        Heap heap = new Heap(new int[]{});

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
        assertEquals("Heap is empty", exception.getMessage());
    }

    @Test
    @DisplayName("Выброс исключения при опустошении кучи")
    void testExtractMoreThanSizeThrowsException() {
        Heap heap = new Heap(new int[]{10});
        assertEquals(10, heap.extractMin());

        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    @DisplayName("Выброс NullPointerException при передаче null в конструктор")
    void testNullArrayThrowsException() {
        assertThrows(NullPointerException.class, () -> new Heap(null));
    }
}