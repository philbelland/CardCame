package com.phil.cardgame.service;

import com.phil.cardgame.model.*;
import com.phil.cardgame.repository.EventRepository;
import com.phil.cardgame.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    EventRepository eventRepository;

    private final int MAX_HAND_SIZE = 52;

    public Long createGame(){
        return gameRepository.addGame();
    }
    public Game deleteGame(long id){
        return gameRepository.removeGame(id);
    }
    public Game findGame(long id){
        return gameRepository.getGame(id);
    }
    public Player findPlayerInGame(long gameId, String playerId){
        Player player = null;
        Game game = findGame(gameId);
        if(game != null){
            player = game.getPlayer(playerId);
        }
        return player;
    }
    public Deck addDeckToGame(long gameId){
        Game game = gameRepository.getGame(gameId);
        Deck deck = new Deck();
        game.addDeck(deck);
        return deck;
    }
    public Player addPlayerToGame(long gameId, String playerId){
        Game game = gameRepository.getGame(gameId);
        Player player = new Player(playerId);
        game.addPlayer(playerId, player);
        return player;
    }
    public Player removePlayerFromGame(long gameId, String playerId){
        Game game = gameRepository.getGame(gameId);
        if(game != null){
            return game.removePlayer(playerId);
        } else {
            return null;
        }
    }
    public long getHandValue(long gameId, String playerId){
        Game game = gameRepository.getGame(gameId);
        Player player = game.getPlayer(playerId);
        return player.getHandValue();
    }
    public List<Card> getPlayerCards(long gameId, String playerId) {
        List<Card> list = null;
        Player player = findPlayerInGame(gameId, playerId);
        if(player != null){
            list = player.getHand();
        }
        return list;
    }
    public synchronized Map<String, Integer> getPlayersWithHandTotals(long gameId){
        Map<String, Integer> map = new LinkedHashMap<>();
        Game game = gameRepository.getGame(gameId);
        if(game != null) {
            if(game.getPlayerList() != null){
                List<Player> playerList = new ArrayList<>(game.getPlayerList());
                Collections.sort(playerList);
                for(Player player : playerList){
                    map.put(player.getId(),player.getHandValue());
                }
            }
        }
        return map;
//        return map.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue())
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1, LinkedHashMap::new));
    }
    public synchronized Map<String,Integer> getUndealtCards(long gameId){
        Map<String,Integer> map = new HashMap<>();
        Game game = gameRepository.getGame(gameId);
        for(Card card : game.getGameDeck()){
            String suit = card.getSuit().name();
//            map.merge(suit, 1, Integer::sum);
            Integer count = map.get(suit);
            map.put(suit,(count == null) ? 1 : count + 1);
        }
        return map;
    }
    public synchronized int dealToPlayer(long gameId, String playerId, int numberRequested){
        Game game = gameRepository.getGame(gameId);
        if(game == null) {
            return 0;
        }
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int numberToDeal = numberRequested;
        int handSize = player.getHand().size();
        int shoeSize = game.getGameDeck().size();
        if (numberRequested > shoeSize) {
            numberToDeal = shoeSize;
            System.out.printf("%s: numberRequested %d > shoeSize %d - numberToDeal = %d\n", playerId, numberRequested, shoeSize, numberToDeal);
        }
        if (handSize + numberRequested > MAX_HAND_SIZE) {
            numberToDeal = MAX_HAND_SIZE - handSize;
            System.out.printf("handSize %d + numberToDeal > MAX_HAND_SIZE - numberToDeal = %d\n", handSize, numberToDeal);
        }
        player.addCards(game.useCards(numberToDeal));
        return numberToDeal;
    }

    public List<Event> getEvents() {
        return eventRepository.getEvents().values().stream().toList();
    }
}
