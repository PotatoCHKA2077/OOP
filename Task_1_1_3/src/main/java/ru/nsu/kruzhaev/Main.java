package ru.nsu.kruzhaev;

import java.util.Scanner;

/**
 * {@code Main} класс.
 */
public class Main {
    /**
     * Функция запуска {@code main}.
     */
    static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String exp = scanner.nextLine();

        Expression e = new ExpressionParser(exp).getRes();

        e.print();

        System.out.println("Введите переменную для взятия производной:");
        String var = scanner.nextLine();

        Expression de = e.derivative(var);
        de.print();

        System.out.println("Введите переменные и их значения в формате" +
                " \"имя1 = значение1; имя2 = значение2;...\" для вычисления выражения:");
        String vars = scanner.nextLine();

        int result = e.eval(vars);
        System.out.println(result);
    }
}
