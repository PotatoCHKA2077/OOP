package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DeckTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    @DisplayName("Проверка на правильное количество карт.")
    public void testNumberOfCardAfterCreation(int num) {
        Deck deck = new Deck(num);
        assertEquals(num, deck.getNumberOfDecks());
        assertEquals(52 * num, deck.getNumberOfCards());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    @DisplayName("Проверка на уменьшение количество карт при выдаче.")
    public void testNumberOfCardAfterGivingCard(int num) {
        Deck deck = new Deck(num);
        deck.giveCard();
        assertEquals(52 * num - 1, deck.getNumberOfCards());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    @DisplayName("Проверка на не нулевое значение выданной карты.")
    public void testNotNullGivenCards(int num) {
        Deck deck = new Deck(num);
        for (int i = 0; i < num * 52; i++) {
            assertNotNull(deck.giveCard());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    @DisplayName("Проверка на ошибку при выдаче карты из пустой колоды.")
    public void testGiveCardFromEmptyList(int num) {
        Deck deck = new Deck(num);
        for (int i = 0; i < num * 52; i++) {
            assertNotNull(deck.giveCard());
        }
        assertThrows(ArrayIndexOutOfBoundsException.class, deck::giveCard);
    }

    @Test
    @DisplayName("Проверка на создание колоды с нулевым количеством колод.")
    public void testNullSizeDeckInitialization() {
        assertThrows(NegativeArraySizeException.class, () -> new Deck(0));
    }
}