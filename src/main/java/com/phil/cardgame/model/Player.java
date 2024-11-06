package com.phil.cardgame.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player implements Comparable<Player> {
    private String id;
    private List<Card> hand;

    public Player(String id) {
        this.hand = new ArrayList<Card>();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public synchronized void setId(String id) {
        this.id = id;
    }

    public List<Card> getHand() {
        return hand;
    }

    public synchronized void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public synchronized boolean addCard(Card card){
        return hand.add(card);
    }

    public synchronized boolean addCards(List<Card> cards){
        return hand.addAll(cards);
    }

    public synchronized boolean removeCard(Card card){
        return hand.remove(card);
    }

    public int getHandValue(){
        int sum = 0;
        for (Card c : hand){
            sum += c.getRank().getValue();
        }
        return sum;
    }

    public void showHand(){
        hand.stream()
                .sorted(Comparator
                        .comparing(Card::getSuit)
                        .thenComparing(Card::getRank))
                .forEach(System.out::println);
    }

    @Override
    public int compareTo(Player o) {
        // ascending
        // return this.getHandValue() - o.getHandValue();
        return o.getHandValue() - this.getHandValue();
    }
}
