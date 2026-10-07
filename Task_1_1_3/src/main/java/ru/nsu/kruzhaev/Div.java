package ru.nsu.kruzhaev;

public class Div extends BinaryExpression{
    public Div(Expression left, Expression right) {
        super(left, right);
        string = "(" + left.getString() + "/" + right.getString()  + ")";
    }

    public Div(String expression) {
        super(expression);
    }

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

    @Override
    public int eval(String variables) {
        return left.eval(variables) / right.eval(variables);
    }
}
