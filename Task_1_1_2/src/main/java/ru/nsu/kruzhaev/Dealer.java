package ru.nsu.kruzhaev;

/**
 * Класс дилера.
 */
public class Dealer {
    private Hand hand;

    /**
     * Конструктор класса {@code Dealer}. Создает руку дилера.
     */
    public Dealer() {
        hand = new Hand();
    }

    /**
     * Метод, отвечающий за логику действия дилера. Дилер берёт карты, пока количество очков у карт
     * в его руке не превысит или не станет равным 17.
     *
     * @param deck Колода, которая участвует в игре.
     * @return Если дилер берёт карту, то метод вернёт эту карту, а если нет, то {@code null}.
     */
    public Card action(Deck deck) {
        if (hand.getPoints() < 17) {
            Card card = deck.giveCard();
            hand.addCard(card);
            return card;
        }
        return null;
    }

    /**
     * Метод, который возвращает руку дилера.
     *
     * @return Рука дилера.
     */
    public Hand getHand() {
        return hand;
    }
}
