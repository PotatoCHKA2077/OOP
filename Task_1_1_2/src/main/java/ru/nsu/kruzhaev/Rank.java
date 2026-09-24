package ru.nsu.kruzhaev;

/**
 * Класс номинала карты.
 */
public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11);

    private int points;

    /**
     * Конструктор класса {@code Rank}.
     *
     * @param n Количество очков номинала карты.
     */
    Rank(int n) {
        points = n;
    }

    /**
     * Возвращает количество очков номинала карты.
     *
     * @return Количество очков.
     */
    public int getPoints() {
        return points;
    }

}
