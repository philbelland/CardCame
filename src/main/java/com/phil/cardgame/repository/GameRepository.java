package com.phil.cardgame.repository;

import com.phil.cardgame.model.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class GameRepository {
    private final AtomicLong nextId = new AtomicLong(1);
    private final HashMap<Long, Game> games = new HashMap<>();

    public Game getGame(long id){
        return games.get(id);
    }

    public synchronized Long addGame(){
        Long id = nextId.getAndIncrement();
        games.put(id, new Game());
        return id;
    }

    public synchronized Game removeGame(long id){
        return games.remove(id);
    }
}
