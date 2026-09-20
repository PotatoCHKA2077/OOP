package ru.nsu.kruzhaev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    @Test
    @DisplayName("Проверка корректного запуска игры через main.")
    void testMainExecution() {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        String input = "1\n0\n0\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Main.main(new String[]{});

        String output = testOut.toString();

        assertTrue(output.contains("Добро пожаловать в Блэкджек!"));
        assertTrue(output.contains("Раунд 1"));
        assertTrue(output.contains("Игра окончена..."));
        assertTrue(output.contains("Спасибо за игру!"));
    }
}