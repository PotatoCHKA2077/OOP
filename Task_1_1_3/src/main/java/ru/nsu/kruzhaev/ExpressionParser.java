package ru.nsu.kruzhaev;

public class ExpressionParser {
    Expression res;
    String string;
    int index;

    public ExpressionParser(String string) {
        isNotEmpty(string);

        this.string = string;
        index = 0;
        res = defineExpression();

        isEnded();
    }

    private Expression defineExpression() {
        if (Character.isDigit(string.charAt(index))) {
            return parseNumber();
        } else if (Character.isLetter(string.charAt(index))) {
            return parseVariable();
        } else if (string.charAt(index++) == '(') {
            return parseExpression();
        } else {
            throw new IllegalArgumentException("Wrong exception: symbol \"" + string.charAt(index) + "\" is invalid!");
        }
    }

    private Expression parseExpression() {
        Expression left = defineExpression();
        char operand = string.charAt(index++);
        Expression right = defineExpression();

        Expression result;
        switch (operand) {
            case '+' -> result = new Add(left, right);
            case '-' -> result = new Sub(left, right);
            case '*' -> result = new Mul(left, right);
            case '/' -> result = new Div(left, right);
            default -> throw new IllegalArgumentException("Unexpected operand: \"" + operand + "\"! Expected: '+', '-', '*' or '/'");
        }

        if (index >= string.length() || string.charAt(index++) != ')'){
            throw new IllegalArgumentException("\")\" missing!");
        }

        return result;
    }

    private Expression parseNumber() {
        int num = 0;
        for (; index < string.length() && Character.isDigit(string.charAt(index)); index++) {
            num *= 10;
            num += Character.digit(string.charAt(index), 10);
        }
        return new Number(num);
    }

    private Expression parseVariable() {
        StringBuilder str = new StringBuilder();
        for (; index < string.length() && Character.isLetter(string.charAt(index)); index++) {
            str.append(string.charAt(index));
        }
        return new Variable(str.toString());
    }

    private void isNotEmpty(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        if (string.isEmpty()) {
            throw new IllegalArgumentException("Expression is empty!");
        }
    }

    private void isEnded(){
        if (index < string.length()) {
            throw new IllegalArgumentException("Illegal expression format!");
        }
    }

    public Expression getRes() {
        return res;
    }
}
