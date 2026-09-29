package ru.nsu.kruzhaev;

/**
 * Класс "Куча" для сортировки кучей путем складывания элементов в кучу.
 */
public class Heap {
    private int[] buffer;
    private int size;

    /**
     * Метод для инициализации кучи.
     *
     * @param arr массив.
     */
    public Heap(int[] arr) {
        if (arr == null){
            throw new NullPointerException();
        }

        size = 0;
        buffer = new int[arr.length];

        for (int elem : arr) {
            insert(elem);
        }
    }

    /**
     * Метод для просеивания элемента вверх кучи.
     *
     * @param index индекс элемента.
     */
    private void siftUp(int index) {
        while (index > 0) {
            int currIdx = index;
            int parentIdx = (index - 1) / 2;

            if (buffer[currIdx] < buffer[parentIdx]) {
                int t = buffer[currIdx];
                buffer[currIdx] = buffer[parentIdx];
                buffer[parentIdx] = t;
                index = parentIdx;
            } else {
                break;
            }
        }
    }

    /**
     * Метод для просеивания элемента вниз кучи.
     *
     * @param index индекс элемента.
     */
    private void siftDown(int index) {
        while (2 * index + 1 < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int min = left;

            if (right < size && buffer[right] < buffer[left]) {
                min = right;
            }

            if (buffer[index] > buffer[min]) {
                int t = buffer[index];
                buffer[index] = buffer[min];
                buffer[min] = t;
                index = min;
            } else {
                break;
            }
        }
    }

    /**
     * Метод для добавления элемента в кучу.
     *
     * @param val значение элемента.
     */
    private void insert(int val) {
        buffer[size++] = val;
        siftUp(size - 1);
    }

    /**
     * Метод достающий минимальный элемент из кучи.
     *
     * @return минимальный элемент кучи.
     */
    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int res = buffer[0];
        buffer[0] = buffer[--size];

        if (size > 0) {
            siftDown(0);
        }
        return res;
    }
}
