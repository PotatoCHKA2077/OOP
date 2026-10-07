package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubTest {
    Expression sub;

    @BeforeEach
    void setUp() {
        sub = new Sub(new Variable("a"), new Number(3));
    }

    @Test
    @DisplayName("Проверка возвращаемого строкового значения .")
    void testGettingString() {
        assertEquals("(a-3)", sub.getString());
    }

    @Test
    @DisplayName("Проверка взятия производной от разницы.")
    void testDerivative() {
        Expression dn = sub.derivative("a");
        assertEquals("(1-0)", dn.getString());
        assertEquals(1, dn.eval("a = 7"));
    }

    @Test
    @DisplayName("Проверка возвращаемого числового значения.")
    void testEval() {
        assertEquals(5, sub.eval("x = 10; a = 8"));
    }

    @Test
    @DisplayName("Проверка печати.")
    void testPrint() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        sub.print();
        assertEquals("(a-3)", output.toString().trim());
    }

    @Test
    @DisplayName("Проверка парсинга из строки.")
    void testParse() {
        Expression sub2 = new Sub("(a-3)");

        assertTrue(sub2.equals(sub));
    }
}