package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VariableTest {
    Expression variable;

    @BeforeEach
    void setUp() {
        variable = new Variable("x");
    }

    @Test
    @DisplayName("Проверка возвращаемого строкового значения переменной.")
    void testGettingString() {
        assertEquals("x", variable.getString());
    }

    @Test
    @DisplayName("Проверка взятия производной от переменной.")
    void testNotZeroDerivative() {
        Expression dn = variable.derivative("x");
        assertEquals(1, dn.eval("x = 7"));
    }
    @Test
    @DisplayName("Проверка взятия производной от другой переменной.")
    void testZeroDerivative() {
        Expression dn = variable.derivative("y");
        assertEquals(0, dn.eval("y = 6"));
    }

    @Test
    @DisplayName("Проверка возвращаемого числового значения.")
    void testEval() {
        assertEquals(10, variable.eval("x = 10; y = 8"));
    }

    @Test
    @DisplayName("Проверка выброса исключения при отсутствии переменной в переданной строке.")
    void testNoNeededVariable() {
        assertThrows(NoSuchElementException.class, () -> variable.eval("f = 3"));
    }

    @Test
    @DisplayName("Проверка печати.")
    void testPrint() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        variable.print();
        assertEquals("x", output.toString().trim());
    }

    @Test
    @DisplayName("Проверка парсинга из строки.")
    void testParse() {
        Expression var = new Variable("x");

        assertTrue(var.equals(variable));
    }
}