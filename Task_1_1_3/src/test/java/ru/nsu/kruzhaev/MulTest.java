package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MulTest {
    Expression mul;

    @BeforeEach
    void setUp() {
        mul = new Mul(new Variable("a"), new Number(3));
    }

    @Test
    @DisplayName("Проверка возвращаемого строкового значения .")
    void testGettingString() {
        assertEquals("(a*3)", mul.getString());
    }

    @Test
    @DisplayName("Проверка взятия производной от производной.")
    void testDerivative() {
        Expression dn = mul.derivative("a");
        assertEquals("((1*3)+(a*0))", dn.getString());
        assertEquals(3, dn.eval("a = 7"));
    }

    @Test
    @DisplayName("Проверка возвращаемого числового значения.")
    void testEval() {
        assertEquals(24, mul.eval("x = 10; a = 8"));
    }

    @Test
    @DisplayName("Проверка печати.")
    void testPrint() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        mul.print();
        assertEquals("(a*3)", output.toString().trim());
    }

    @Test
    @DisplayName("Проверка парсинга из строки.")
    void testParse() {
        Expression mul2 = new Mul("(a*3)");

        assertTrue(mul2.equals(mul));
    }
}