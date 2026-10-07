package ru.nsu.kruzhaev;

/**
 * Класс константы.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Конструктор класса {@code Number} с передачей числа.
     *
     * @param val Число, к которому приравнивается константа.
     */
    public Number(int val) {
        value = val;
        string = Integer.toString(value);
    }

    /**
     * Конструктор класса {@code Number} с передачей строки.
     *
     * @param expression Строка с числом, к которому приравнивается константа.
     */
    public Number(String expression) {
        Expression exp = new ExpressionParser(expression).getRes();
        if (exp.getClass() != Number.class) {
            throw new IllegalArgumentException("Это не константа!");
        }
        value = exp.eval("");
        string = exp.getString();
    }

    /**
     * Метод для взятия производной.
     *
     * @param variable Переменная, по которой будет браться производная.
     * @return Константу со значением 0 т.к. производная от константы равна 0.
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Метод для вычисления значения.
     *
     * @param variables Переменные и их значения для вычисления.
     * @return Значение константы.
     */
    @Override
    public int eval(String variables) {
        return value;
    }
}
