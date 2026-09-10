package ru.nsu.kruzhaev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void SumTest() {
        assertEquals(3, Main.sum(2, 1));
    }
}