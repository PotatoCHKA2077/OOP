package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardList;
    private int points;
    private boolean haveAceElevenPoints;

    public Hand() {
        cardList = new ArrayList<Card>();
        points = 0;
        haveAceElevenPoints = false;
    }

    public void takeCard(Card card) {
        cardList.add(card);
        points += card.getRank().getPoints();
        if (card.getRank() == Rank.ACE) {
            if (points + 10 <= 21 && !haveAceElevenPoints) {
                points += 10;
                haveAceElevenPoints = true;
            } else if (points - 10 <= 21 && haveAceElevenPoints){
                points -= 10;
                haveAceElevenPoints = false;
            }
        }
    }
}
