package ru.nsu.kruzhaev;

public class Number extends Expression{
    private final int value;

    public Number(int val) {
        value = val;
        string = Integer.toString(value);
    }

    public Number(String expression) {
        Expression exp = new ExpressionParser(expression).getRes();
        if (exp.getClass() != Number.class) {
            throw new IllegalArgumentException("Это не константа!");
        }
        value = exp.eval("");
        string = exp.getString();
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(String variables) {
        return value;
    }
}
