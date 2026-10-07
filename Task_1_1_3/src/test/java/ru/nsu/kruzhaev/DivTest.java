package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DivTest {
    Expression div;

    @BeforeEach
    void setUp() {
        div = new Div(new Variable("a"), new Number(3));
    }

    @Test
    @DisplayName("Проверка возвращаемого строкового значения .")
    void testGettingString() {
        assertEquals("(a/3)", div.getString());
    }

    @Test
    @DisplayName("Проверка взятия производной от частного.")
    void testDerivative() {
        Expression dn = div.derivative("a");
        assertEquals("(((1*3)-(a*0))/(3*3))", dn.getString());
        assertEquals(0, dn.eval("a = 7"));
    }

    @Test
    @DisplayName("Проверка возвращаемого числового значения.")
    void testEval() {
        assertEquals(3, div.eval("x = 10; a = 9"));
    }

    @Test
    @DisplayName("Проверка печати.")
    void testPrint() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        div.print();
        assertEquals("(a/3)", output.toString().trim());
    }

    @Test
    @DisplayName("Проверка парсинга из строки.")
    void testParse() {
        Expression div2 = new Div("(a/3)");

        assertTrue(div2.equals(div));
    }
}