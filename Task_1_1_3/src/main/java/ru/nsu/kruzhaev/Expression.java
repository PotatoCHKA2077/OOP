package ru.nsu.kruzhaev;

import java.util.Objects;

public abstract class Expression {
    protected String string;

    public abstract Expression derivative(String variable);

    public abstract int eval(String variables);

    public void print() {
        String str = this.getString();
        if (str == null) {
            throw new NullPointerException();
        }
        System.out.println(str);
    }

    public String getString() {
        return string;
    }

    public boolean equals(Expression exp) {
        return (
                Objects.equals(string, exp.getString())
                && this.getClass() == exp.getClass()
        );
    }
}
