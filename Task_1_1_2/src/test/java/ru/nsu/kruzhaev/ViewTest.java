package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

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

        assertEquals("Раунд 1\n" +
                "Дилер раздал карты:\n" +
                "\tВаши карты: [Туз Пики (11), Шестёрка Буби (6)] => 17\n" +
                "\tКарты дилера: [Восьмёрка Червы (8), <закрытая карта>]",
                outputStream.toString().trim());
    }

    @Test
    @DisplayName("Проверка правильного вывода печати хода игрока.")
    public void testPrintPlayerMove() {
        assertDoesNotThrow(view::playerMove);
    }

    @Test
    @DisplayName("Проверка правильного вывода печати хода дилера.")
    public void tesPrintDealerMove() {
        view.dealerMove();

        assertEquals("Ход дилера\n" +
                "-------\n" +
                "Дилер открывает закрытую карту Десятка Крести (10)\n" +
                "\tВаши карты: [Туз Пики (11), Шестёрка Буби (6)] => 17\n" +
                "\tКарты дилера: [Восьмёрка Червы (8), Десятка Крести (10)] => 18",
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

    private String stringCard(Card card) {
        String answer = "";
        if (card.isClose()) {
            answer += "<закрытая карта>";
        } else {
            if (card.getRank() == Rank.JACK || card.getRank() == Rank.KING) {
                switch (card.getSuit()) {
                    case HEARTS -> answer += "Червовый ";
                    case SPADES -> answer += "Пиковый ";
                    case DIAMONDS -> answer += "Бубновый ";
                    default -> answer += "Крестовый ";
                }
                if (card.getRank() == Rank.JACK) {
                    answer += "Валет ";
                } else {
                    answer += "Король ";
                }
            } else if (card.getRank() == Rank.QUEEN) {
                switch (card.getSuit()) {
                    case HEARTS -> answer += "Червовая Дама ";
                    case SPADES -> answer += "Пиковая Дама ";
                    case DIAMONDS -> answer += "Бубновая Дама ";
                    default -> answer += "Крестовая Дама ";
                }
            } else {
                switch (card.getRank()) {
                    case TWO -> answer += "Двойка ";
                    case THREE -> answer += "Тройка ";
                    case FOUR -> answer += "Четвёрка ";
                    case FIVE -> answer += "Пятёрка ";
                    case SIX -> answer += "Шестёрка ";
                    case SEVEN -> answer += "Семёрка ";
                    case EIGHT -> answer += "Восьмёрка ";
                    case NINE -> answer += "Девятка ";
                    case TEN -> answer += "Десятка ";
                    default -> answer += "Туз ";
                }
                switch (card.getSuit()) {
                    case HEARTS -> answer += "Червы ";
                    case SPADES -> answer += "Пики ";
                    case DIAMONDS -> answer += "Буби ";
                    default -> answer += "Крести ";
                }
            }

            answer += "(" + card.getPoints() + ")";
        }
        return answer;
    }
}