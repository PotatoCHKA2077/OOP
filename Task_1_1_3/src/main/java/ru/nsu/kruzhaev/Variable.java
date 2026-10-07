package ru.nsu.kruzhaev;

import java.util.NoSuchElementException;

public class Variable extends Expression{
    public Variable(String name) {
        string = name;
    }

    @Override
    public Expression derivative(String variable) {
        if (variable.equals(string)) {
            return new Number(1);
        }
        return new Number(0);
    }

    @Override
    public int eval(String variables) {
        VariablesParser vars = new VariablesParser(variables);
        if (!vars.getVariables().containsKey(string)) {
            throw new NoSuchElementException("Variable \"" + string + "\" doesn't exist!");
        }

        return vars.getVariables().get(string);
    }
}
