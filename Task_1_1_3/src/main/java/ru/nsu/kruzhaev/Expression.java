package ru.nsu.kruzhaev;

import java.util.Objects;

/**
 * Абстрактный класс выражения.
 */
public abstract class Expression {
    protected String string;

    /**
     * Абстрактный метод взятия производной.
     *
     * @param variable Переменная по которой будет браться производная.
     * @return Функция производной.
     */
    public abstract Expression derivative(String variable);

    /**
     * Абстрактный метод вычисления значения выражения.
     *
     * @param variables Переменные необходимые для вычисления значения выражения.
     * @return Значение выражения.
     */
    public abstract int eval(String variables);

    /**
     * Метод для печати выражения в виде строки.
     */
    public void print() {
        String str = this.getString();
        if (str == null) {
            throw new NullPointerException();
        }
        System.out.println(str);
    }

    /**
     * Метод возвращающий строковое значение выражения.
     *
     * @return Строковое значение выражения.
     */
    public String getString() {
        return string;
    }

    /**
     * Метод для проверки равенства двух выражений.
     *
     * @param exp Выражение для проверки.
     * @return Равны или нет.
     */
    public boolean equals(Expression exp) {
        return (
                Objects.equals(string, exp.getString())
                && this.getClass() == exp.getClass()
            );
    }
}
