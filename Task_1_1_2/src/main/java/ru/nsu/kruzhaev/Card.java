package ru.nsu.kruzhaev;

/**
 * Класс карты.
 */
public class Card {
    private Rank rank;
    private Suit suit;
    private int points;
    private boolean close;

    /**
     * Конструктор класса {@code Card}.
     * Берет на вход номинал и масть и устанавливает их карте.
     * Также устанавливает карте необходимое количество очков (в зависимости от номинала) и
     * состояние "закрытости" карты (по умолчанию открыта).
     *
     * @param rank Номинал карты.
     * @param suit Масть карты.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        points = rank.getPoints();
        close = false;
    }

    /**
     * Метод, который возвращает номинал карты.
     *
     * @return Номинал карты.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Метод, который возвращает масть карты.
     *
     * @return Масть карты.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Метод, который возвращает количество очков номинала карты.
     *
     * @return Количество очков карты.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Метод, который устанавливает количество очков номинала карты.
     *
     * @param points Количество очков, которое надо установить.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Метод, который проверяет закрытая ли карта.
     *
     * @return Состояние "закрытости" карты.
     */
    public boolean isClose() {
        return close;
    }

    /**
     * Метод, который устанавливает "закрытость" карты.
     *
     * @param val "Закрытость" карты.
     */
    public void setClose(boolean val) {
        close = val;
    }
}
