package ru.nsu.kruzhaev;

/**
 * Абстрактный класс выражения с оператором.
 */
public abstract class BinaryExpression extends Expression {
    protected Expression left;
    protected Expression right;
    /**
     * Конструктор класса {@code BinaryExpression} с передачей выражений.
     *
     * @param left Левое выражение.
     * @param right Правое выражение.
     */
    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Конструктор класса {@code BinaryExpression} с передачей строки с выражением.
     *
     * @param expression Строка с выражением.
     */
    public BinaryExpression(String expression) {
        BinaryExpression exp = (BinaryExpression) new ExpressionParser(expression).getRes();
        left = exp.getLeft();
        right = exp.getRight();
        string = exp.getString();
    }

    /**
     * Метод возвращающий левое выражение.
     *
     * @return Левое выражение.
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Метод возвращающий правое выражение.
     *
     * @return Правое выражение.
     */
    public Expression getRight() {
        return right;
    }
}
