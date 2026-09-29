package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Player player;
    private Deck deck;

    @BeforeEach
    void setUp() {
        player = new Player();
        deck = new Deck(1);
    }

    @Test
    @DisplayName("Проверка взятия карты игроком.")
    void testActionTakeCard() {
        int numberOfCards = deck.getNumberOfCards();
        Card card = player.action(1, deck);

        assertNotNull(card);
        assertEquals(1, player.getHand().getCardList().size());
        assertEquals(numberOfCards - 1, deck.getNumberOfCards());
    }

    @Test
    @DisplayName("Проверка невзятия карты игроком.")
    void testActionStop() {
        int initialDeckSize = deck.getNumberOfCards();
        Card card = player.action(0, deck);

        assertNull(card);
        assertEquals(0, player.getHand().getCardList().size());
        assertEquals(initialDeckSize, deck.getNumberOfCards());
    }
}