package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cardList;

    public Deck(int numberOfDecks) {
        Suit[] availableSuits = Suit.values();
        Rank[] availableRanks = Rank.values();

        cardList = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < numberOfDecks; i++){
            for (Suit st : availableSuits){
                for (Rank rnk : availableRanks){
                    cardList.add(new Card(rnk, st));
                }
            }
        }
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void shuffleDeck() {
        Collections.shuffle(cardList);
    }

    public Card giveCard() {
        return cardList.removeLast();
    }
}
