package ru.nsu.kruzhaev;

public class Sub extends BinaryExpression{
    public Sub(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "-" + right.getString()  + ")";
    }

    public Sub(String expression) {
        super(expression);
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) - right.eval(variables);
    }
}
