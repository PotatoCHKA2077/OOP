package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandTest {
    private Hand hand;

    @BeforeEach
    void setUp() {
        hand = new Hand();
    }

    @Test
    @DisplayName("Проверка обычного сложения.")
    public void testBasePointsSum() {
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.CLUBS));

        assertEquals(17, hand.getPoints());
    }

    @Test
    @DisplayName("Проверка изменения количества очков у туза при переполнении.")
    public void testAceChangingValueOnBust() {
        hand.addCard(new Card(Rank.NINE, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.SIX, Suit.CLUBS));

        assertEquals(16, hand.getPoints());
    }

    @Test
    @DisplayName("Проверка изменения количества очков у одного из двух тузов при переполнении.")
    public void testOneOfAceChangingValueOnBust() {
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.ACE, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.NINE, Suit.CLUBS));

        assertEquals(21, hand.getPoints());
    }

    @Test
    @DisplayName("Проверка изменения количества очков у обоих тузов при переполнении.")
    public void testDoubleAceChangingValueOnBust() {
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.KING, Suit.CLUBS));
        hand.addCard(new Card(Rank.ACE, Suit.CLUBS));

        assertEquals(19, hand.getPoints());
    }

    @Test
    @DisplayName("Проверка подсчета очков для блэкджека.")
    public void testBlackJackPoints() {
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.QUEEN, Suit.HEARTS));

        assertEquals(21, hand.getPoints());
    }

    @Test
    @DisplayName("Проверка подсчета большого количества очков.")
    public void testBasicBustSum() {
        hand.addCard(new Card(Rank.QUEEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.FIVE, Suit.SPADES));
        hand.addCard(new Card(Rank.JACK, Suit.CLUBS));

        assertEquals(25, hand.getPoints());
    }

    @Test
    @DisplayName("Проверка очистки руки.")
    public void testClearing() {
        hand.addCard(new Card(Rank.QUEEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.FIVE, Suit.SPADES));
        hand.addCard(new Card(Rank.JACK, Suit.CLUBS));
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.KING, Suit.CLUBS));
        hand.addCard(new Card(Rank.ACE, Suit.CLUBS));

        hand.clear();

        assertEquals(0, hand.getPoints());
        assertTrue(hand.getCardList().isEmpty());
    }

    @Test
    @DisplayName("Проверка передачи нулевого параметра при добавлении карты.")
    public void testAddNullCard() {
        assertThrows(NullPointerException.class, () -> hand.addCard(null));
    }
}