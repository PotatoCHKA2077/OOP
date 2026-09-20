package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

class ControllerRefactoredTest {
    @Test
    @DisplayName("Проверка на отсутствие ошибок при запуске игры")
    void testStartGameWithCustomScanner() {
        String input = "1\n0\n0\n";
        Scanner customScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Controller controller = new Controller(customScanner);

        assertDoesNotThrow(controller::start);
    }
}