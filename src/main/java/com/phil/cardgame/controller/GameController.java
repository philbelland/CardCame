package com.phil.cardgame.controller;

import com.phil.cardgame.model.*;
import com.phil.cardgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/games", produces = "application/json")
public class GameController {
//    @Autowired
//    GameService service;
    private GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping(value = "/createGame")
    public ResponseEntity<Long> createGame(){
        return new ResponseEntity<>(service.createGame(), HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/deleteGame/{gameId}")
    public ResponseEntity<Game> deleteGame(@PathVariable long gameId){
        Game game = service.deleteGame(gameId);
        return new ResponseEntity<>(game, game == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    @PutMapping(value = "/addPlayer/{gameId}/{player_id}")
    public ResponseEntity<Player> addPlayer(@PathVariable long gameId, @PathVariable String player_id){
        return new ResponseEntity<>(service.addPlayerToGame(gameId, player_id), HttpStatus.OK);
    }
    @PutMapping(value = "/dealCards/{gameId}/{playerId}/dealCard")
    public ResponseEntity<Integer> dealCard(@PathVariable long gameId, @PathVariable String playerId){
        if(service.findPlayerInGame(gameId, playerId) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int numberOfCardsDealt = service.dealToPlayer(gameId, playerId, 1);
        return new ResponseEntity<>(numberOfCardsDealt, HttpStatus.OK);
    }
    @PutMapping(value = "/dealCards/{gameId}/{playerId}/{numberOfCardsRequested}")
    public ResponseEntity<Integer> dealCards(@PathVariable long gameId, @PathVariable String playerId, @PathVariable int numberOfCardsRequested){
        if(service.findPlayerInGame(gameId, playerId) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int numberOfCardsDealt = service.dealToPlayer(gameId, playerId, numberOfCardsRequested);
        return new ResponseEntity<>(numberOfCardsDealt, HttpStatus.OK);
    }
    @PutMapping(value = "/addDeck/{game_id}")
    public ResponseEntity<Deck> addDeck(@PathVariable long gameId){
        Deck deck = service.addDeckToGame(gameId);
        return new ResponseEntity<>(deck, HttpStatus.OK);
    }
    @GetMapping(value = "/getHand/{gameId}/{playerId}")
    public ResponseEntity<List<Card>> getHand(@PathVariable long gameId, @PathVariable String playerId){
        List<Card> hand = null;
        HttpStatus status;
        if(service.findPlayerInGame(gameId, playerId) == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            hand = service.getPlayerCards(gameId, playerId);
            if(hand.isEmpty()){
                status = HttpStatus.NO_CONTENT;
            } else {
                status = HttpStatus.OK;
            }
        }
        return new ResponseEntity<>(service.getPlayerCards(gameId, playerId), status);
    }
    @GetMapping(value = "/getPlayers/{gameId}")
    public ResponseEntity<Map<String,Integer>> getPlayers(@PathVariable long gameId){
        Map<String,Integer> details = service.getPlayersWithHandTotals(gameId);
        HttpStatus status;
        if(details.isEmpty()){
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(details, status);
    }
    @GetMapping(value = "/getUndealtCards/{gameId}")
    public ResponseEntity<Map<String,Integer>> getUndealtCards(@PathVariable long gameId) {
        Map<String,Integer> details = service.getUndealtCards(gameId);
        HttpStatus status;
        if(details.isEmpty()){
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(details, status);
    }
    @DeleteMapping(value = "/removePlayer/{gameId}/{playerId}")
    public ResponseEntity<Player> removePlayer(@PathVariable long gameId, @PathVariable String playerId){
        HttpStatus status;
        Player player = service.removePlayerFromGame(gameId, playerId);
        if(player == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(player, status);
    }
    @GetMapping(value = "/viewEvents")
    public ResponseEntity<List<Event>> viewEvents(){
        return new ResponseEntity<>(service.getEvents(),HttpStatus.OK);
    }
}
