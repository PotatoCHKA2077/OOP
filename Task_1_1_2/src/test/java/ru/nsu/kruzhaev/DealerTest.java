package ru.nsu.kruzhaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    private Dealer dealer;
    private Deck deck;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
        deck = new Deck(1);
    }

    @Test
    @DisplayName("Проверка взятия карты дилером если количество очков меньше 17.")
    void testActionTakesCardWhenPointsLessThan17() {
        dealer.getHand().addCard(new Card(Rank.TEN, Suit.CLUBS));
        dealer.getHand().addCard(new Card(Rank.SIX, Suit.SPADES));

        Card card = dealer.action(deck);
        assertNotNull(card);
        assertEquals(3, dealer.getHand().getCardList().size());
    }

    @Test
    @DisplayName("Проверка невзятия карты дилером если количество очков больше 17.")
    void testActionReturnsNullWhenPointsExceedOrEqual17() {
        dealer.getHand().addCard(new Card(Rank.TEN, Suit.CLUBS));
        dealer.getHand().addCard(new Card(Rank.EIGHT, Suit.SPADES));

        Card card = dealer.action(deck);

        assertNull(card);
        assertEquals(2, dealer.getHand().getCardList().size());
    }
}