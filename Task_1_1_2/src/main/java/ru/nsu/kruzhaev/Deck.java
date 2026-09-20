package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Класс колоды/колод карт (в зависимости от количества).
 */
public class Deck {
    private List<Card> cardList;
    private int numberOfDecks;

    /**
     * Конструктор класса {@code Deck}.
     * Берёт на вход количество стандартных колод из 52 карт и создает общую колоду.
     *
     * @param numberOfDecks Количество стандартных колод в общей колоде устанавливаемое в начале
     *                      игры. Обычно используют до четырёх стандартных колод. Чем больше колод
     *                      тем у игрока меньше шанс обыграть казино.
     */
    public Deck(int numberOfDecks) {
        if (numberOfDecks == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        this.numberOfDecks = numberOfDecks;

        Suit[] availableSuits = Suit.values();
        Rank[] availableRanks = Rank.values();

        cardList = new ArrayList<>();

        for (int i = 0; i < this.numberOfDecks; i++) {
            for (Suit st : availableSuits) {
                for (Rank rnk : availableRanks) {
                    cardList.add(new Card(rnk, st));
                }
            }
        }

        Collections.shuffle(cardList);
    }

    /**
     * Метод, который выдает верхнюю карту с колоды.
     *
     * @return Верхняя карта.
     */
    public Card giveCard() {
        if (cardList.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return cardList.removeLast();
    }

    /**
     * Метод, который возвращает количество стандартных колод в общей колоде.
     *
     * @return Номинал карты.
     */
    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    /**
     * Метод, который возвращает количество оставшихся карт в колоде.
     *
     * @return Номинал карты.
     */
    public int getNumberOfCards() {
        return cardList.size();
    }
}
