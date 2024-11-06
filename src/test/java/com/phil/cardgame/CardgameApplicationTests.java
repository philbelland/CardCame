package com.phil.cardgame;

import com.phil.cardgame.model.*;
import com.phil.cardgame.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@SpringBootTest
@ComponentScan(basePackages={"com.phil.cardgame"})
public class CardgameApplicationTests {
    @Autowired
    private GameService service;

    @Test
    void testDeck() {
        Deck deck = new Deck();
        System.out.println(deck);
    }

    @Test
    void testShoe() {
        Game game = new Game();
        List<Card> hand = game.useCards(5);
        System.out.println(">>> Hand = " + hand);
        game.addDeck(new Deck());
        System.out.println(">>> # cards after adding a deck = " + game.getGameDeck().size());
    }

    @Test
    void testPlayer() {
        Game game = new Game();
        Player player = new Player("name@google.com");
        player.setHand(game.useCards(5));
        System.out.println(">>> " + player.getId() + " " + player.getHand());
    }

    @Test
    void testGame(){
        long gameId = service.createGame();
        Game game = service.findGame(gameId);
        // game.addDeck(new Deck());
        String playerId = "myemail@address.com";
        Player player = new Player(playerId);
        game.addPlayer(playerId, player);
        game.dealToPlayer(playerId, 15);
        System.out.printf("> %s's hand: %s%n", player.getId(), player.getHand());
        // player.showHand();
        Map<String,Integer> remaining = service.getUndealtCards(gameId);
        System.out.printf("> Player: " + playerId + " cards left: %n" + remaining);
    }

    @Test
    void testConcurrent() throws InterruptedException {
        long gameId = service.createGame();
        int numberOfCards = 15;
        class concurrentGame extends Thread{
            private String playerId;
            public concurrentGame(String playerId){
                this.playerId = playerId;
            }
            @Override
            public void run(){
                System.out.printf("> %d Player %s: %d\n",
                        this.getId(),
                        playerId,
                        System.currentTimeMillis());
                service.addPlayerToGame(gameId, playerId);
                service.dealToPlayer(gameId, playerId, numberOfCards);
                if(playerId.equals("player3")){
                    service.removePlayerFromGame(1,"player1");
                }
                System.out.printf("> %d Player %s: %s\n",
                        this.getId(),
                        playerId,
                        service.findPlayerInGame(gameId,playerId).getHand());
            }
        }
        List<String> players = List.of("player1","player2","player3","player4");
        players.forEach(p -> {
            new concurrentGame(p).start();
        });
        Thread.sleep(5000);
        System.out.println(">>> UNDEALT: " + service.getUndealtCards(gameId));

        Map<Long, Event> events = service.getEvents();
        for(Long i:events.keySet()){
            System.out.println(i + ":" + events.get(i));
        }
    }

    @Test
    void removeAll(){
        List<Card> deck = new ArrayList<Card>();
        deck.add(new Card(Card.Suits.CLUBS, Card.Ranks.SEVEN));
        deck.add(new Card(Card.Suits.CLUBS, Card.Ranks.SIX));
        deck.add(new Card(Card.Suits.CLUBS, Card.Ranks.EIGHT));
        deck.add(new Card(Card.Suits.CLUBS, Card.Ranks.NINE));

//		List<Card> del = new ArrayList<Card>();
//		del.add(new Card(Card.Suits.CLUBS, Card.Ranks.EIGHT));
//		del.add(new Card(Card.Suits.CLUBS, Card.Ranks.NINE));

        List<Card> del = new ArrayList<>(deck.subList(0,2));

        System.out.println(">>> DECK: " + deck.size());
        System.out.println(">>> DEL :\n" + del);
        deck.removeAll(del);
        System.out.println(">>> DECK:\n" + deck);
    }
}
