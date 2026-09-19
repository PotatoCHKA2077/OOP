package ru.nsu.kruzhaev;

import java.util.List;
import java.util.Scanner;

/**
 * Класс, который отвечает за взаимодействие игрока с консолью: вывод и считывание информации.
 */
public class View {
    private Player player;
    private Dealer dealer;
    private Deck deck;

    /**
     * Конструктор класса {@code View}. Берёт на вход игрока, дилера и колоду участвующие в игре для будущего взаимодействия с ними.
     * @param player Игрок.
     * @param dealer Дилер.
     * @param deck Колода.
     */
    public View(Player player, Dealer dealer, Deck deck) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
    }

    /**
     * Метод, печатающий начало раунда.
     * @param round Текущий раунд.
     */
    public void printRound(int round) {
        System.out.println("Раунд " + round);
        System.out.println("Дилер раздал карты:");
        printTable();
    }

    /**
     * Метод печатающий карту: её номинал, масть и количество очков.
     * @param card Карта, которую необходимо распечатать.
     */
    private void printCard(Card card) {
        if (card.isClose()) {
            System.out.print("<закрытая карта>");
        } else {
            if (card.getRank() == Rank.JACK || card.getRank() == Rank.KING) {
                switch (card.getSuit()) {
                    case HEARTS -> System.out.print("Червовый ");
                    case SPADES -> System.out.print("Пиковый ");
                    case DIAMONDS -> System.out.print("Бубновый ");
                    case CLUBS -> System.out.print("Крестовый ");
                }
                switch (card.getRank()) {
                    case JACK -> System.out.print("Валет ");
                    case KING -> System.out.print("Король ");
                }
            } else if (card.getRank() == Rank.QUEEN) {
                switch (card.getSuit()) {
                    case HEARTS -> System.out.print("Червовая Дама ");
                    case SPADES -> System.out.print("Пиковая Дама ");
                    case DIAMONDS -> System.out.print("Бубновая Дама ");
                    case CLUBS -> System.out.print("Крестовая Дама ");
                }
            } else {
                switch (card.getRank()) {
                    case TWO -> System.out.print("Двойка ");
                    case THREE -> System.out.print("Тройка ");
                    case FOUR -> System.out.print("Четвёрка ");
                    case FIVE -> System.out.print("Пятёрка ");
                    case SIX -> System.out.print("Шестёрка ");
                    case SEVEN -> System.out.print("Семёрка ");
                    case EIGHT -> System.out.print("Восьмёрка ");
                    case NINE -> System.out.print("Девятка ");
                    case TEN -> System.out.print("Десятка ");
                    case ACE -> System.out.print("Туз ");
                }
                switch (card.getSuit()) {
                    case HEARTS -> System.out.print("Червы ");
                    case SPADES -> System.out.print("Пики ");
                    case DIAMONDS -> System.out.print("Буби ");
                    case CLUBS -> System.out.print("Крести ");
                }
            }

            System.out.print("(" + card.getPoints() + ")");
        }
    }

    /**
     * Метод, печатающий карты в руке и сумму очков этих карт.
     * @param hand Рука, которую необходимо распечатать.
     */
    private void printHand(Hand hand){
        System.out.print("[");
        List<Card> cards = hand.getCardList();
        for (int i = 0; i < cards.size() - 1; i++) {
            printCard(cards.get(i));
            System.out.print(", ");
        }
        Card card = cards.getLast();
        printCard(card);
        System.out.print("]");
        if (!card.isClose()) {
            System.out.print(" => " + hand.getPoints());
        }
        System.out.print("\n");
    }

    /**
     * Метод, печатающий карты на столе: карты игрока и дилера.
     */
    private void printTable() {
        System.out.print("\tВаши карты: ");
        printHand(player.getHand());

        System.out.print("\tКарты дилера: ");
        printHand(dealer.getHand());
    }

    /**
     * Метод, обрабатывающий действия игрока.
     */
    public void playerMove(){
        System.out.println("Ваш ход\n-------");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться...");

        Card result = player.action(scanner.nextInt(), deck);
        while (result != null && player.getHand().getPoints() <= 21){
            System.out.print("Вы открыли карту ");
            printCard(result);
            System.out.print("\n");

            printTable();

            System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться...");
            result = player.action(scanner.nextInt(), deck);
        }
        if (player.getHand().getPoints() > 21){
            printTable();
        }
    }

    /**
     * Метод, обрабатывающий действия дилера.
     */
    public void dealerMove() {
        System.out.println("Ход дилера\n-------");

        Card lastCard = dealer.getHand().getCardList().getLast();
        if (lastCard.isClose()){
            System.out.print("Дилер открывает закрытую карту ");
            lastCard.setClose(false);
            printCard(lastCard);
            System.out.print("\n");

            printTable();
        }
        if (dealer.getHand().getPoints() < 17) {
            Card result = dealer.action(deck);
            while (result != null && dealer.getHand().getPoints() <= 21) {
                System.out.print("Дилер открывает карту ");
                printCard(result);
                System.out.print("\n");

                printTable();

                result = dealer.action(deck);
            }
            System.out.print("Дилер открывает карту ");
            printCard(dealer.getHand().getCardList().getLast());
            System.out.print("\n");

            printTable();

        }
    }

    /**
     * Метод, выводящий уведомление о победе игрока.
     * @param playerWins Количество побед игрока.
     * @param dealerWins Количество побед дилера.
     */
    private void printScore(int playerWins, int dealerWins) {
        if (playerWins > dealerWins) {
            System.out.println("Счёт " + playerWins + ":" + dealerWins + " в вашу пользу.");
        } else if (playerWins < dealerWins){
            System.out.println("Счёт " + dealerWins + ":" + playerWins + " в пользу дилера.");
        } else {
            System.out.println("Счёт " + playerWins + ":" + dealerWins);
        }
    }

    /**
     * Метод, выводящий счёт.
     * @param playerWins Количество побед игрока.
     * @param dealerWins Количество побед дилера.
     */
    public void playerWin(int playerWins, int dealerWins) {
        System.out.print("Вы выйграли раунд! ");
        printScore(playerWins, dealerWins);
    }

    /**
     * Метод, выводящий уведомление о проигрыше игрока.
     * @param playerWins Количество побед игрока.
     * @param dealerWins Количество побед дилера.
     */
    public void playerLosing(int playerWins, int dealerWins) {
        System.out.print("Вы проиграли раунд! ");
        printScore(playerWins, dealerWins);
    }

    /**
     * Метод, выводящий уведомление о ничьей.
     * @param playerWins Количество побед игрока.
     * @param dealerWins Количество побед дилера.
     */
    public void draw(int playerWins, int dealerWins) {
        System.out.print("У вас ничья! ");
        printScore(playerWins, dealerWins);
    }
}
