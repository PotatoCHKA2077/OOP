package ru.nsu.kruzhaev;

import java.util.Scanner;

/**
 * Класс, отвечающий за всю логику игры: создание игры, раздачу карт, порядок игры и т.д.
 */
public class Controller {
    private Scanner scanner;
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private View view;
    private int playerWins;
    private int dealerWins;

    /**
     * Конструктор класса {@code Controller}.
     */
    public Controller() {
        this(new Scanner(System.in));
    }

    /**
     * Конструктор класса {@code Controller}, предназначенный для удобства при тестировании.
     *
     * @param scanner Сканер потока ввода.
     */
    public Controller(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Создает игрока, дилера, колоду. Начинает игру.
     */
    public void start() {
        System.out.println("Добро пожаловать в Блэкджек!\nВведите количество колод для игры:");
        int numberOfDecks = scanner.nextInt();

        player = new Player();
        dealer = new Dealer();
        deck = new Deck(numberOfDecks);
        view = new View(player, dealer, deck, scanner);
        playerWins = 0;
        dealerWins = 0;

        startRound();
    }

    /**
     * Метод, отвечающий за порядок действий и логику раунда: раздача первых карт, обработка
     * условий победы и поражения, отправление информации классу {@code View}.
     */
    private void startRound() {
        int play = 1;
        for (int round = 1; play == 1; round++) {
            int numOfDecks = deck.getNumberOfDecks();
            if (deck.getNumberOfCards() <= numOfDecks * 52 / 3 || numOfDecks == 1) {
                deck = new Deck(numOfDecks);
            }

            player.getHand().clear();
            dealer.getHand().clear();
            player.getHand().addCard(deck.giveCard());
            dealer.getHand().addCard(deck.giveCard());
            player.getHand().addCard(deck.giveCard());

            Card card = deck.giveCard();
            if (dealer.getHand().getPoints() + card.getPoints() != 21) {
                card.setClose(true);
            }
            dealer.getHand().addCard(card);

            view.printRound(round);

            if (player.getHand().getPoints() == 21) {
                if (player.getHand().getPoints() == dealer.getHand().getPoints()) {
                    view.draw(playerWins, dealerWins);
                } else {
                    view.playerWin(++playerWins, dealerWins);
                }
            } else if (dealer.getHand().getPoints() == 21) {

                view.playerLosing(playerWins, ++dealerWins);
            } else {
                playerMove();

                if (player.getHand().getPoints() > 21) {
                    view.playerLosing(playerWins, ++dealerWins);
                } else {
                    dealerMove();

                    int playerPoints = player.getHand().getPoints();
                    int dealerPoints = dealer.getHand().getPoints();

                    if (dealerPoints < playerPoints || dealerPoints > 21) {
                        view.playerWin(++playerWins, dealerWins);
                    } else if (dealerPoints > playerPoints) {
                        view.playerLosing(playerWins, ++dealerWins);
                    } else {
                        view.draw(playerWins, dealerWins);
                    }
                }
            }

            System.out.println("Введите \"1\" если хотите продолжить, "
                    + "или \"0\" если хотите закончить игру...");
            play = scanner.nextInt();
        }
        System.out.println("Игра окончена...\nСпасибо за игру!");
    }

    private void playerMove() {
        System.out.println("Ваш ход\n-------");

        Card result = player.getHand().getCardList().get(1); // not null value.
        while (result != null && player.getHand().getPoints() <= 21) {
            System.out.println("Введите \"1\", чтобы взять карту, " +
                    "и \"0\", чтобы остановиться...");
            result = player.action(scanner.nextInt(), deck);

            if (result != null) {
                view.playerOpen(result);
            }
        }
    }

    private void dealerMove() {
        System.out.println("Ход дилера\n-------");

        Card card = dealer.getHand().getCardList().get(1);
        while (card != null && dealer.getHand().getPoints() <= 21) {
            view.dealerOpen(card);
            card = dealer.action(deck);

            if (dealer.getHand().getPoints() > 21) {
                view.dealerOpen(card);
            }
        }

    }

    public Dealer getDealer() {
        return dealer;
    }

    public Player getPlayer() {
        return player;
    }
}
