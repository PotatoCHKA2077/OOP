package ru.nsu.kruzhaev;

/**
 * Класс разницы.
 */
public class Sub extends BinaryExpression {
    /**
     * Конструктор класса {@code Sub} с передачей выражений.
     *
     * @param left Левое выражение разницы.
     * @param right Правое выражение разницы.
     */
    public Sub(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "-" + right.getString()  + ")";
    }

    /**
     * Конструктор класса {@code Sub} с передачей строки с разностью.
     *
     * @param expression Строка с разностью.
     */
    public Sub(String expression) {
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
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Метод вычисления разницы.
     *
     * @param variables Переменные необходимые для вычисления значения выражения.
     * @return Числовое значение разницы.
     */
    @Override
    public int eval(String variables) {
        return left.eval(variables) - right.eval(variables);
    }
}
