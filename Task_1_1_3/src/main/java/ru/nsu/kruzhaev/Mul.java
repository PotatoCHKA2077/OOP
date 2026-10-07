package ru.nsu.kruzhaev;

public class Mul extends BinaryExpression{
    public Mul(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "*" + right.getString()  + ")";
    }

    public Mul(String expression) {
        super(expression);
    }

    @Override
    public Expression derivative(String variable) { // (f * g)' = f'*g + f*g'
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) * right.eval(variables);
    }
}
