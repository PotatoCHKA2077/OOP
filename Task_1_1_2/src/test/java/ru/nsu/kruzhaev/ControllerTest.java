package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControllerTest {
    @Test
    @DisplayName("Проверка на отсутствие ошибок при запуске игры")
    void testStartGameWithCustomScanner() {
        String input = "1\n0\n0\n";
        Scanner customScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Controller controller = new Controller(customScanner);

        assertDoesNotThrow(controller::start);
    }

    @Test
    @DisplayName("Проверка выйгрыша при Блэкджеке у игрока.")
    void testPlayerBlackJack() {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        while (true) {
            String input = "1\n0\n0\n";
            Scanner customScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

            Controller controller = new Controller(customScanner);
            assertDoesNotThrow(controller::start);

            Player player = controller.getPlayer();
            Dealer dealer = controller.getDealer();

            if (havePlayerBlackJack(player) && !haveDealerBlackJack(dealer)) {
                assertTrue(testOut.toString().contains("Вы выйграли раунд!"));
                break;
            }
        }
    }

    @Test
    @DisplayName("Проверка выйгрыша при Блэкджеке у дилера.")
    void testDealerBlackJack() {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        while (true) {
            String input = "1\n0\n0\n";
            Scanner customScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

            Controller controller = new Controller(customScanner);
            assertDoesNotThrow(controller::start);

            Player player = controller.getPlayer();
            Dealer dealer = controller.getDealer();
            if (!havePlayerBlackJack(player) && haveDealerBlackJack(dealer)) {
                assertTrue(testOut.toString().contains("Вы проиграли раунд!"));
                break;
            }
        }
    }

    @Test
    @DisplayName("Проверка ничьи при Блэкджеке у игрока и дилера.")
    void testStartDraw() {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        while (true) {
            String input = "1\n0\n0\n";
            Scanner customScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

            Controller controller = new Controller(customScanner);
            assertDoesNotThrow(controller::start);

            Player player = controller.getPlayer();
            Dealer dealer = controller.getDealer();
            if (havePlayerBlackJack(player) && haveDealerBlackJack(dealer)) {
                assertTrue(testOut.toString().contains("У вас ничья!"));
                break;
            }
        }
    }

    boolean havePlayerBlackJack(Player player) {
        Card fst = player.getHand().getCardList().get(0);
        Card snd = player.getHand().getCardList().get(1);
        return fst.getPoints() == 10 && snd.getPoints() == 11 ||
                fst.getPoints() == 11 && snd.getPoints() == 10;
    }

    boolean haveDealerBlackJack(Dealer dealer) {
        Card fst = dealer.getHand().getCardList().get(0);
        Card snd = dealer.getHand().getCardList().get(1);
        return fst.getPoints() == 10 && snd.getPoints() == 11 ||
                fst.getPoints() == 11 && snd.getPoints() == 10;
    }
}