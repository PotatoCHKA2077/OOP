package ru.nsu.kruzhaev;

public abstract class BinaryExpression extends Expression{
    protected Expression left;
    protected Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public BinaryExpression(String expression) {
        BinaryExpression exp = (BinaryExpression) new ExpressionParser(expression).getRes();
        left = exp.getLeft();
        right = exp.getRight();
        string = exp.getString();
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}
