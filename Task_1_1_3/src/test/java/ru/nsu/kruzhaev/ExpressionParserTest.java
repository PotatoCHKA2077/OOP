package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ExpressionParserTest {
    static Stream<String> illegalExpressionProvideMethod() {
        return Stream.of(
                "(x=5)",
                "(x%2)",
                "(x+3",
                "x+3",
                "x=4",
                "(2+3)abc",
                ""
        );
    }

    @Test
    @DisplayName("Проверка парсинга числа")
    void testNumberParsing() {
        Expression e = new ExpressionParser("258").getRes();
        assertTrue(e.equals(new Number(258)) && e.getClass() == Number.class);
    }

    @Test
    @DisplayName("Проверка парсинга переменной")
    void testVariableParsing() {
        Expression e = new ExpressionParser("variable").getRes();
        assertTrue(
                e.equals(new Variable("variable"))
                && e.getClass() == Variable.class
        );
    }

    @Test
    @DisplayName("Проверка парсинга суммы")
    void testAddParsing() {
        Expression e = new ExpressionParser("(2+3)").getRes();
        assertTrue(e.equals(new Add("(2+3)")) && e.getClass() == Add.class);
    }

    @Test
    @DisplayName("Проверка парсинга разницы")
    void testSubParsing() {
        Expression e = new ExpressionParser("(2-3)").getRes();
        assertTrue(e.equals(new Sub("(2-3)")) && e.getClass() == Sub.class);
    }

    @Test
    @DisplayName("Проверка парсинга произведения")
    void testMulParsing() {
        Expression e = new ExpressionParser("(2*3)").getRes();
        assertTrue(e.equals(new Add("(2*3)")) && e.getClass() == Mul.class);
    }

    @Test
    @DisplayName("Проверка парсинга частного")
    void testDivParsing() {
        Expression e = new ExpressionParser("(2/3)").getRes();
        assertTrue(e.equals(new Add("(2/3)")) && e.getClass() == Div.class);
    }

    @Test
    @DisplayName("Проверка парсинга вложенного выражения")
    void testExpressionParsing() {
        Expression e = new ExpressionParser("(3+(2*x))").getRes();
        assertTrue(
                e.equals(new Add(new Number(3), new Mul(new Number(2),
                        new Variable("x"))))
        );
    }

    @ParameterizedTest
    @MethodSource("illegalExpressionProvideMethod")
    @DisplayName("Проверка выдачи ошибки при некорректной строке выражения")
    void testIllegalExpression(String str) {
        assertThrows(IllegalArgumentException.class, () -> new ExpressionParser(str));
    }

    @Test
    @DisplayName("Проверка выдачи ошибки при передаче null")
    void testNullExpression() {
        assertThrows(NullPointerException.class, () -> new ExpressionParser(null));
    }
}