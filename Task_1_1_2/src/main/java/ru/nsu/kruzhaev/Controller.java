package ru.nsu.kruzhaev;

import java.util.Scanner;

/**
 * Класс, отвечающий за всю логику игры: создание игры, раздачу карт, порядок игры и т.д.
 */
public class Controller {
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private View view;
    private int round;
    private int playerWins;
    private int dealerWins;

    /**
     * Конструктор класса {@code Controller}. Создает игрока, дилера, колоду. Начинает игру.
     */
    public Controller() {
        System.out.println("Добро пожаловать в Блэкджек!\nВведите количество колод для игры:");
        Scanner scanner = new Scanner(System.in);
        int numberOfDecks = scanner.nextInt();

        player = new Player();
        dealer = new Dealer();
        deck = new Deck(numberOfDecks);
        view = new View(player, dealer, deck);
        round = 1;
        playerWins = 0;
        dealerWins = 0;

        startRound();
    }

    /**
     * Метод, отвечающий за порядок действий и логику раунда: раздача первых карт, обработка условий победы и поражения, отправление информации классу {@code View}.
     */
    private void startRound() {
        view.printRound(round);

        player.getHand().addCard(deck.giveCard());
        dealer.getHand().addCard(deck.giveCard());
        player.getHand().addCard(deck.giveCard());

        Card card = deck.giveCard();
        card.setClose(true);
        dealer.getHand().addCard(card);


        if (player.getHand().getPoints() == 21) {
            view.playerWin(playerWins, dealerWins);
        } else{
            view.playerMove();
            if (player.getHand().getPoints() > 21) {
                view.playerLosing(playerWins, ++dealerWins);
            } else {
                view.dealerMove();
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

        System.out.println("Введите \"1\" если хотите продолжить, или \"0\" если хотите закончить игру...");
        Scanner scanner = new Scanner(System.in);
        int numOfDecks = deck.getNumberOfDecks();
        if (scanner.nextInt() == 1) {
            if (deck.getNumberOfCards() <= numOfDecks * 52 / 3 || numOfDecks == 1) {
                deck = new Deck(numOfDecks);
            }

            player.getHand().clear();
            dealer.getHand().clear();

            round++;
            startRound();
        }
        System.out.println("Игра окончена...\nСпасибо за игру!");
    }
}
