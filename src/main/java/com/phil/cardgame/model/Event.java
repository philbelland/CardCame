package com.phil.cardgame.model;

public class Event {
    private long timestamp;
    private String action;
    private long gameId;
    private String playerId;

    public Event(long timestamp, String action, long gameId, String playerId) {
        this.timestamp = timestamp;
        this.action = action;
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                ", action='" + action + '\'' +
                ", gameId=" + gameId +
                ", playerId='" + playerId + '\'' +
                '}';
    }

    public String getAction() {
        return action;
    }

    public long getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
