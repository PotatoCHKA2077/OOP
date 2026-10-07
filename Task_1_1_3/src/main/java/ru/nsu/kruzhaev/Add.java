package ru.nsu.kruzhaev;

/**
 * Класс суммы.
 */
public class Add extends BinaryExpression {
    /**
     * Конструктор класса {@code Add} с передачей выражений.
     *
     * @param left Левое выражение суммы.
     * @param right Правое выражение суммы.
     */
    public Add(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "+" + right.getString()  + ")";
    }

    /**
     * Конструктор класса {@code Add} с передачей строки с суммой.
     *
     * @param expression Строка с суммой.
     */
    public Add(String expression) {
        super(expression);
    }

    /**
     * Метод взятия производной от переменной.
     *
     * @param variable Переменная по которой будет браться производная.
     * @return Выражение функции производной.
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Метод вычисления суммы.
     *
     * @param variables Переменные необходимые для вычисления значения выражения.
     * @return Числовое значение суммы.
     */
    @Override
    public int eval(String variables) {
        return left.eval(variables) + right.eval(variables);
    }
}
