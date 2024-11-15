package com.phil.cardgame;

import com.phil.cardgame.model.*;
import com.phil.cardgame.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.*;

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
    void testDeckSize() {
        Deck deck = new Deck();
        System.out.println(deck);
        assertEquals(52,deck.getCards().size());
    }

    @Test
    void testDeckOrder() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        boolean same = true;
        for(int i = 0; i < deck1.getCards().size() && same; i++){
            same = deck1.getCards().get(i).equals(deck2.getCards().get(i));
            if(!same){
                System.out.println("Diff at card " + i);
            }
        }
        assertFalse(same);
    }

    @Test
    void testShoe() {
        Game game = new Game();
        List<Card> hand = game.useCards(5);
        assertEquals(5, hand.size());
        assertEquals(47, game.getGameDeck().size());
        System.out.println(">>> Hand = " + hand);
        game.addDeck(new Deck());
        assertEquals(47 + 52, game.getGameDeck().size());
        System.out.println(">>> # cards after adding a deck = " + game.getGameDeck().size());
    }

    @Test
    void testPlayer() {
        Game game = new Game();
        Player player = new Player("name@google.com");
        player.setHand(game.useCards(5));
        System.out.println(">>> " + player.getId() + " " + player.getHand());

        Card card = new Card(Card.Suits.DIAMONDS,Card.Ranks.TEN);

        int value1 = player.getHandValue();
        player.addCard(card);
        int value2 = player.getHandValue();
        System.out.println(">>> " + player.getId() + " " + player.getHand());

        assertEquals(value1 + card.getRank().getValue(), value2);

        player.removeCard(card);
        int value3 = player.getHandValue();
        System.out.println(">>> " + player.getId() + " " + player.getHand());

        assertEquals(value3, value1);
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
        int numberOfCards = 15;
        List<String> players = List.of("player1","player2","player3","player4","player5");
        long gameId = service.createGame();
        class concurrentGame extends Thread{
            private String playerId;
            public concurrentGame(String playerId){
                this.playerId = playerId;
            }
            @Override
            public void run(){
                System.out.printf("> %d Player %s: %d\n",this.getId(),playerId,System.currentTimeMillis());
                service.addPlayerToGame(gameId, playerId);
                int dealt = service.dealToPlayer(gameId, playerId, numberOfCards);
//                if(playerId.equals("player1")){
//                    service.removePlayerFromGame(1,"player1");
//                }
                Player p = service.findPlayerInGame(gameId,playerId);
                List<Card> hand = (p == null) ? null : p.getHand();
                System.out.printf("> %d Player %s: %d %s\n",this.getId(),playerId,dealt,hand);
            }
        }
//        players.forEach(p -> {
//            new concurrentGame(p).start();
//        });
//        Thread.sleep(5000);
        List<concurrentGame> threads = new ArrayList<>();
        players.forEach(p -> {
            concurrentGame thread = new concurrentGame(p);
            threads.add(thread);
            thread.start();
        });
        while(threads.stream().anyMatch(Thread::isAlive)){
            Thread.sleep(1);
        }
        Map<String, Integer> undealtCards = service.getUndealtCards(gameId);
        System.out.println(">>> UNDEALT: " + undealtCards);

        int undealtCount = undealtCards.size();
        int dealtCount = 0;
        for(String playerId: players){
            dealtCount += service.getPlayerCards(gameId,playerId).size();
        }
        assertEquals(0, undealtCount);
        assertEquals(52, dealtCount);

        service.getEvents().forEach(System.out::println);
    }
}
