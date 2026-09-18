package ru.nsu.kruzhaev;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Deck deck = new Deck(2);
        List<Card> cards = deck.getCardList();

        deck.shuffleDeck();
        for (Card card : cards) {
            System.out.println(card.getSuit() + " " + card.getRank());
        }
    }
}
