package com.phil.cardgame.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

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

    public String getTimestampString() {
        return DateTimeFormatter.ISO_INSTANT
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(Instant.ofEpochMilli(timestamp));
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + getTimestampString() +
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
