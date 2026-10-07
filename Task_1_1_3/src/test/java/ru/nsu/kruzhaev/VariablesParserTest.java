package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class VariablesParserTest {
    static Stream<String> standardVarsMethod() {
        return Stream.of(
                "x = 5; test = 10",
                "x =5; test= 10",
                "x=5; test=10",
                "x=5;test=10",
                "x=5; y= 3;test =10 ; f = 6"
        );
    }

    static Stream<String> illegalVarsFormatMethod() {
        return Stream.of(
                "x;test=10",
                "x=5; test",
                "x=;test=10",
                "test="
        );
    }

    static Stream<String> notIntVarsValueMethod() {
        return Stream.of(
                "x = abc; test = 10",
                "x = 5; test= x",
                "x = 5; test = 10.5",
                "x = 5-1; test=10",
                "x = 6x; test=4y",
                "x = 5; test = 5*x"
        );
    }

    @ParameterizedTest
    @MethodSource("standardVarsMethod")
    @DisplayName("Обычная строка с переменными.")
    void testStandardString(String str) {
        VariablesParser vp = new VariablesParser(str);

        assertEquals(5, vp.getVariables().get("x"));
        assertEquals(10, vp.getVariables().get("test"));
    }

    @ParameterizedTest
    @MethodSource("illegalVarsFormatMethod")
    @DisplayName("Обычная строка с переменными.")
    void testIllegalVariableFormat(String str) {
        assertThrows(IllegalArgumentException.class, () -> new VariablesParser(str));
    }

    @ParameterizedTest
    @MethodSource("notIntVarsValueMethod")
    @DisplayName("Обычная строка с переменными.")
    void testVariableWithNotIntValue(String str) {
        assertThrows(NumberFormatException.class, () -> new VariablesParser(str));
    }
}