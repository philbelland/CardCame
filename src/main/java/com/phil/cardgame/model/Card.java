package com.phil.cardgame.model;

import java.util.Objects;

public class Card implements Comparable<Card>{

    public enum Suits {
        DIAMONDS,
        HEARTS,
        CLUBS,
        SPADES;
    }

    public enum Ranks {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14);

        private int value;

        Ranks(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Suits suit;
    private Ranks rank;

    public Card(Suits suit, Ranks card){
        this.suit = suit;
        this.rank = card;
    }

    @Override
    public int compareTo(Card o) {
        if(this.suit.equals(o.suit)){
            return this.rank.getValue() - o.rank.getValue();
        } else {
            return this.suit.ordinal() - o.suit.ordinal();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(suit);
        result = 31 * result + Objects.hashCode(rank);
        return result;
    }

    public Suits getSuit() {
        return suit;
    }

    public Ranks getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
