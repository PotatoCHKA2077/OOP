package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberTest {
    Expression number;

    @BeforeEach
    void setUp() {
        number = new Number(3);
    }

    @Test
    @DisplayName("Проверка возвращаемого строкового значения числа.")
    void testGettingString() {
        assertEquals("3", number.getString());
    }

    @Test
    @DisplayName("Проверка взятия производной от числа.")
    void testDerivative() {
        Expression dn = number.derivative("var");
        assertEquals(0, dn.eval("var = 7"));
    }

    @Test
    @DisplayName("Проверка возвращаемого числового значения.")
    void testEval() {
        assertEquals(3, number.eval("x = 10; y = 8"));
    }

    @Test
    @DisplayName("Проверка печати.")
    void testPrint() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        number.print();
        assertEquals("3", output.toString().trim());
    }

    @Test
    @DisplayName("Проверка парсинга из строки.")
    void testParse() {
        Expression number2 = new Number("3");
        assertTrue(number2.equals(number));
    }
}