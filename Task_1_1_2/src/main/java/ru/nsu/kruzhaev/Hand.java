package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс руки с картами.
 */
public class Hand {
    private List<Card> cardList;
    private int points;
    private int elevenPointsAceIndex;

    /**
     * Конструктор класса {@code Hand}. Создает список, в который в будущем будут добавляться карты, а также переменную
     * для суммы очков карт в этой руке.
     */
    public Hand() {
        cardList = new ArrayList<Card>();
        points = 0;
        elevenPointsAceIndex = -1;
    }

    /**
     * Метод для взятия карты в руку. При необходимости пересчитывает количество очков у тузов.
     * @param card Карта, которую необходимо добавить в руку.
     */
    public void addCard(Card card) {
        cardList.add(card);
        if (card.getRank() == Rank.ACE) {
            if (points + 11 <= 21 && elevenPointsAceIndex == -1){
                elevenPointsAceIndex = cardList.size() - 1;
            } else {
                card.setPoints(1);
            }
        }
        points += card.getRank().getPoints();

        if (points > 21 && elevenPointsAceIndex != -1){
            cardList.get(elevenPointsAceIndex).setPoints(1);
            points -= 10;
            elevenPointsAceIndex = -1;
        }
    }

    /**
     * Метод, который возвращает сумму очков у карт в руке.
     * @return Количество очков в руке.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Метод, который возвращает список карт в руке.
     * @return Список карт в руке
     */
    public List<Card> getCardList() {
        return cardList;
    }

    /**
     * Метод, который обнуляет руку.
     */
    public void clear() {
        cardList = new ArrayList<Card>();
        points = 0;
        elevenPointsAceIndex = -1;
    }
}
