package com.phil.cardgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(){
        cards = new ArrayList<Card>();
        for(Card.Suits suit : Card.Suits.values()){
            for(Card.Ranks card : Card.Ranks.values()){
                cards.add(new Card(suit, card));
            }
        }
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
    public synchronized void shuffleCards() {
        Collections.shuffle(cards);
    }
/*
game deck
    current deck = [0]
    deck[0]: 5 cards
    deck[1]: 52 cards

    deal 6 cards
    - 5 cards from deck[0]
    - delete deck[0]
    - 1 card from deck[0]
 */
//    public synchronized List<Card> useCards(int number){
//        List<Card> cards = new ArrayList<>(gameDeck.subList(0,Math.max(gameDeck.size(),number)));
//        gameDeck.removeAll(cards);
//        return cards;
//    }
    @Override
    public String toString() {
        StringBuilder deckString = new StringBuilder("Deck{");
        for(Card card : cards){
            deckString.
                    append(System.lineSeparator()).
                    append(card.getRank()).
                    append(" of ").
                    append(card.getSuit()).
                    append(",");
        }
        deckString.deleteCharAt(deckString.lastIndexOf(",")).
                append(System.lineSeparator()).
                append('}');
        return deckString.toString();
    }
}
