package ru.nsu.kruzhaev;

public class Add extends BinaryExpression{
    public Add(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "+" + right.getString()  + ")";
    }

    public Add(String expression) {
        super(expression);
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) + right.eval(variables);
    }
}
