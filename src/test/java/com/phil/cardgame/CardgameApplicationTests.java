package com.phil.cardgame;

import com.phil.cardgame.model.*;
import com.phil.cardgame.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ComponentScan(basePackages={"com.phil.cardgame"})
class CardgameApplicationTests {

	@Autowired
	GameService service;

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
		System.out.println(">>> " + player.getEmail() + " " + player.getHand());
	}

	@Test
	void testGame(){
		Game game = new Game();
		// game.addDeck(new Deck());
		String email = "myemail@address.com";
		Player player = new Player(email);
		game.addPlayer(email, player);
		game.dealToPlayer(email, 5);
		System.out.printf(">>> %s's hand: %s%n", player.getEmail(), player.getHand());
		player.showHand();
	}

	@Test
	void testUndealt(){
		service.createGame();
		service.addPlayerToGame(1, "phil");
		service.dealToPlayer(1,"phil", 40);
		Map<String,Integer> remaining = service.getUndealtCards(1);
		System.out.printf(">>> " + remaining);
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
