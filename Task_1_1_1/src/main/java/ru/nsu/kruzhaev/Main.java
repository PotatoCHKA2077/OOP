 package ru.nsu.kruzhaev;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int sum(int a, int b){
        return a+b;
    }

    static void main() {
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <=2; j++) {
                System.out.println(i + " + " + j + " = " + sum(i, j));
            }
        }
    }
}
