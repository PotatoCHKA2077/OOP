package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ViewTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private View view;

    @BeforeEach
    public void setUpOutput() {
        player = new Player();
        dealer = new Dealer();
        deck = new Deck(1);

        String input = "1\n0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        view = new View(player, dealer, deck, scanner);

        player.getHand().addCard(new Card(Rank.ACE, Suit.SPADES));
        player.getHand().addCard(new Card(Rank.SIX, Suit.DIAMONDS));

        dealer.getHand().addCard(new Card(Rank.EIGHT, Suit.HEARTS));
        dealer.getHand().addCard(new Card(Rank.TEN, Suit.CLUBS));
        dealer.getHand().getCardList().get(1).setClose(true);

        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreOutput() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Проверка правильного вывода печати раунда.")
    public void testPrintRound() {
        view.printRound(1);

        assertEquals("Раунд 1\n" + "Дилер раздал карты:\n"
                        + "\tВаши карты: [Туз Пики (11), Шестёрка Буби (6)] => 17\n"
                        + "\tКарты дилера: [Восьмёрка Червы (8), <закрытая карта>]",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Проверка правильного вывода печати открытия карты игроком.")
    public void testPrintPlayerOpen() {
        Card card = new Card(Rank.FIVE, Suit.CLUBS);
        player.getHand().addCard(card);
        view.playerOpen(card);

        assertEquals("Вы открыли карту Пятёрка Крести (5)\n"
                + "\tВаши карты: [Туз Пики (1), Шестёрка Буби (6), Пятёрка Крести (5)] => 12\n"
                + "\tКарты дилера: [Восьмёрка Червы (8), <закрытая карта>]",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Проверка правильного вывода печати открытия карты дилером.")
    public void tesPrintDealerOpen() {
        Card card = new Card(Rank.FIVE, Suit.CLUBS);
        dealer.getHand().addCard(card);
        dealer.getHand().getCardList().get(1).setClose(false);
        view.dealerOpen(card);

        assertEquals("Дилер открывает карту Пятёрка Крести (5)\n"
                        + "\tВаши карты: [Туз Пики (11), Шестёрка Буби (6)] => 17\n"
                        + "\tКарты дилера: [Восьмёрка Червы (8), Десятка Крести (10), "
                        + "Пятёрка Крести (5)] => 23",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Проверка правильного вывода печати открытия закрытой карты дилером.")
    public void tesPrintDealerClosedCardOpen() {
        view.dealerOpen(dealer.getHand().getCardList().get(1));

        assertEquals("Дилер открывает закрытую карту Десятка Крести (10)\n"
                        + "\tВаши карты: [Туз Пики (11), Шестёрка Буби (6)] => 17\n"
                        + "\tКарты дилера: [Восьмёрка Червы (8), Десятка Крести (10)] => 18",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Проверка правильного вывода печати выйгрыша игрока.")
    public void testPrintPlayerWin() {
        view.playerWin(4, 2);

        assertEquals("Вы выйграли раунд! Счёт 4:2 в вашу пользу.",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Проверка правильного вывода печати проигрыша игрока.")
    public void testPrintPlayerLosing() {
        view.playerLosing(1, 5);

        assertEquals("Вы проиграли раунд! Счёт 5:1 в пользу дилера.",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Проверка правильного вывода печати ничьей.")
    public void testPrintDraw() {
        view.draw(8, 8);

        assertEquals("У вас ничья! Счёт 8:8", outputStream.toString().trim());
    }
}