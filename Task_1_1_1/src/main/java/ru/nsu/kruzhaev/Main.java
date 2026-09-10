package ru.nsu.kruzhaev;

import java.util.Arrays;


public class Main {
    public static class Heap {
        private int[] buffer;
        public int size;

        public Heap(int[] arr) {
            size = 0;
            buffer = new int[arr.length];
            for (int elem : arr) {
                insert(elem);
            }
        }

        public void shiftUp(int index) {
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

        public void shiftDown(int index) {
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

        public void insert(int val) {
            buffer[size++] = val;
            shiftUp(size - 1);
        }

        public int extractMin() {
            if (size == 0) {
                throw new IllegalStateException("Heap is empty");
            }
            int res = buffer[0];
            buffer[0] = buffer[--size];
            if (size > 0) {
                shiftDown(0);
            }
            return res;
        }
    }

    public static void heapsort(int[] arr) {
        Heap h = new Heap(arr);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = h.extractMin();
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {5, 4, 8, 3, 2, 6};
        heapsort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
