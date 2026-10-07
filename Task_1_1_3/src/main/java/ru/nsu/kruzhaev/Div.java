package ru.nsu.kruzhaev;

/**
 * Класс частного.
 */
public class Div extends BinaryExpression {
    /**
     * Конструктор класса {@code Div} с передачей выражений.
     *
     * @param left Левое выражение частного.
     * @param right Правое выражение частного.
     */
    public Div(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "/" + right.getString()  + ")";
    }

    /**
     * Конструктор класса {@code Div} с передачей строки с частным.
     *
     * @param expression Строка с частным.
     */
    public Div(String expression) {
        super(expression);
    }

    /**
     * Метод взятия производной от переменной.
     *
     * @param variable Переменная по которой будет браться производная.
     * @return Выражение функции производной.
     */
    @Override
    public Expression derivative(String variable) { // (f / g)' = (f' * g - g' * f) / g^2
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    /**
     * Метод вычисления частного.
     *
     * @param variables Переменные необходимые для вычисления значения выражения.
     * @return Числовое значение частного.
     */
    @Override
    public int eval(String variables) {
        return left.eval(variables) / right.eval(variables);
    }
}
