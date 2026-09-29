package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    @DisplayName("Проверка корректности инициализации карты")
    void testCardInitialization() {
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        assertEquals(Rank.ACE, card.getRank());
        assertEquals(Suit.HEARTS, card.getSuit());
        assertEquals(11, card.getPoints());
        assertFalse(card.isClose());
    }

    @Test
    @DisplayName("Проверка изменения очков карты.")
    void testSetPoints() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        card.setPoints(1);

        assertEquals(1, card.getPoints());
    }

    @Test
    @DisplayName("Проверка изменения состояния \"закрытости\".")
    void testSetClose() {
        Card card = new Card(Rank.KING, Suit.DIAMONDS);
        card.setClose(true);

        assertTrue(card.isClose());

        card.setClose(false);
        assertFalse(card.isClose());
    }
}