package com.phil.cardgame.repository;

import com.phil.cardgame.model.Event;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepository {
    private final AtomicLong nextId = new AtomicLong(1);
    private final HashMap<Long, Event> events = new HashMap<>();

    public Event getEvent(long id){
        return events.get(id);
    }

    public synchronized long addEvent(String action, long gameId, String playerId){
        long id = nextId.getAndIncrement();
        events.put(id, new Event(System.currentTimeMillis(),action,gameId,playerId));
        return id;
    }
}
