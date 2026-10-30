package ru.nsu.kruzhaev;

import java.util.NoSuchElementException;

/**
 * Класс переменной.
 */
public class Variable extends Expression {
    /**
     * Конструктор класса переменной.
     *
     * @param name имя переменной.
     */
    public Variable(String name) {
        string = name;
    }

    /**
     * Метод для взятия производной.
     *
     * @param variable Переменная по которой будет браться производная.
     * @return Константа. 1 если та же переменная и 0 если нет.
     */
    @Override
    public Expression derivative(String variable) {
        if (variable.equals(string)) {
            return new Number(1);
        }
        return new Number(0);
    }

    /**
     * Метод для вычисления числового значения.
     *
     * @param variables Переменные необходимые для вычисления значения выражения.
     * @return Числовое значение.
     */
    @Override
    public int eval(String variables) {
        VariablesParser vars = new VariablesParser(variables);
        if (!vars.getVariables().containsKey(string)) {
            throw new NoSuchElementException("Variable \"" + string + "\" doesn't exist!");
        }

        return vars.getVariables().get(string);
    }
}
