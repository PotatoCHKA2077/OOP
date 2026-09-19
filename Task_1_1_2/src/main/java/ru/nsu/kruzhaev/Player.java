package ru.nsu.kruzhaev;

/**
 * Класс игрока.
 */
public class Player {
    private Hand hand;

    /**
     * Конструктор класса {@code Player}. Создает руку игроку.
     */
    public Player() {
        hand = new Hand();
    }

    /**
     * Метод, отвечающий за логику действия игрока. Если игрок решил взять карту, то ему даётся
     * карта.
     *
     * @param input Ответ игрока: "1" - взять карту, "0" - остановиться.
     * @param deck Колода, которая участвует в игре.
     * @return Если игрок решил взять карту, то метод вернёт эту карту, а если нет, то {@code null}.
     */
    public Card action(int input, Deck deck) {
        if (input == 1) {
            Card card = deck.giveCard();
            hand.addCard(card);
            return card;
        }
        return null;
    }

    /**
     * Метод, который возвращает руку игрока.
     *
     * @return Рука игрока.
     */
    public Hand getHand() {
        return hand;
    }
}
