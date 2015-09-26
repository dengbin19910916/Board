package com.wrox.entities;

import java.time.Instant;

/**
 * Created by dengb on 2015/9/11.
 */
public class ChatMessage {

    private Instant timestamp;
    private Type type;
    private String user;
    private String content;

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public enum Type {
        STARTED, JOINED, ERROR, LEFT, TEXT
    }
}
