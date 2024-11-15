package com.phil.cardgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Map<String, Player> players;
    private List<Card> gameDeck;
    private int round = 0;

    public Game() {
        players = new HashMap<>();
        Deck deck = new Deck();
        deck.shuffleCards();
        gameDeck = deck.getCards();
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void nextRound(){
        round++;
    }

    public List<Player> getPlayerList() {
        return players.values().stream().toList();
    }

    public Player getPlayer(String email) {
        return players.get(email);
    }

    public List<Card> getGameDeck(){ return gameDeck; }

    public synchronized void addDeck(Deck deck) { gameDeck.addAll(deck.getCards()); }

    public synchronized void addPlayer(String email, Player player){
        players.put(email, player);
    }

    public synchronized Player removePlayer(String email){ return players.remove(email); }

    public synchronized void dealToPlayer(String email, int number){
        List<Card> cards = useCards(number);
        players.get(email).addCards(cards);
    }

    public synchronized List<Card> useCards(int number){
        List<Card> cards = new ArrayList<>(gameDeck.subList(0,Math.min(gameDeck.size(),number)));
        gameDeck.removeAll(cards);
        return cards;
    }

    public String showDeck() {
        StringBuilder deckString = new StringBuilder("Deck{");
        for(Card card : gameDeck){
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
