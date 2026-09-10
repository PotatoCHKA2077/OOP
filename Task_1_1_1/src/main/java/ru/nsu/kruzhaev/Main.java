 package ru.nsu.kruzhaev;

import java.util.Arrays;
import java.util.Random;

 //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static class Heap {
        private static int[] buffer;
        public static int size;

        public Heap(int[] arr){
            size = 0;
            buffer = new int[arr.length];
            for (int elem : arr) {
                insert(elem);
            }
        }

        public void shiftUp(int index) {
                while (index > 0) {
                    int curr_idx = index;
                    int parent_idx = (index - 1) / 2;
                    if (buffer[curr_idx] < buffer[parent_idx]) {
                        int t = buffer[curr_idx];
                        buffer[curr_idx] = buffer[parent_idx];
                        buffer[parent_idx] = t;
                        index = parent_idx;
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

                    if (right < size && buffer[right] < buffer[left]){
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

        public void insert(int val){
                buffer[size++] = val;
                shiftUp(size - 1);
        }

        public int extractMin() {
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

    static void main() {
        int[] arr = new int[10];
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            arr[i] = rand.nextInt(300);
        }
        System.out.println(Arrays.toString(arr));
    }
}
