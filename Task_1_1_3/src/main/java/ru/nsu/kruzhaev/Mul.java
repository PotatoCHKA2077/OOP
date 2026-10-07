package ru.nsu.kruzhaev;

/**
 * Класс произведения.
 */
public class Mul extends BinaryExpression {
    /**
     * Конструктор класса {@code Mul} с передачей выражений.
     *
     * @param left Левое выражение произведения.
     * @param right Правое выражение произведения.
     */
    public Mul(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "*" + right.getString()  + ")";
    }

    /**
     * Конструктор класса {@code Mul} с передачей строки с произведением.
     *
     * @param expression Строка с произведением.
     */
    public Mul(String expression) {
        super(expression);
    }

    /**
     * Метод взятия производной от переменной.
     *
     * @param variable Переменная по которой будет браться производная.
     * @return Выражение функции производной.
     */
    @Override
    public Expression derivative(String variable) { // (f * g)' = f'*g + f*g'
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    /**
     * Метод вычисления произведения.
     *
     * @param variables Переменные необходимые для вычисления значения выражения.
     * @return Числовое значение произведения.
     */
    @Override
    public int eval(String variables) {
        return left.eval(variables) * right.eval(variables);
    }
}
