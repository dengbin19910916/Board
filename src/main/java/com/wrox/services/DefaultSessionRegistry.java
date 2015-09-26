package com.wrox.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Created by dengb on 2015/9/10.
 */
@Service
@Scope(value = "singleton")
public class DefaultSessionRegistry implements SessionRegistry {
    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
    private final Set<Consumer<HttpSession>> callbacks = new HashSet<>();
    private final Set<Consumer<HttpSession>> callbacksAddedWhileLocked = new HashSet<>();

    @Override
    public void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void updateSessionId(HttpSession session, String oldSessionId) {
        synchronized (sessions) {
            sessions.remove(oldSessionId);
            addSession(session);
        }
    }

    @Override
    public void removeSession(HttpSession session) {
        sessions.remove(session.getId());
        synchronized (this.callbacks) {
            this.callbacksAddedWhileLocked.clear();
            this.callbacks.forEach(c -> c.accept(session));
            try {
                this.callbacksAddedWhileLocked.forEach(c -> c.accept(session));
            } catch (ConcurrentModificationException ignore) {
            }
        }
    }

    @Override
    public List<HttpSession> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }

    @Override
    public int getNumberOfSessions() {
        return sessions.size();
    }

    @Override
    public void registerOnRemoveCallback(Consumer<HttpSession> callback) {
        this.callbacksAddedWhileLocked.add(callback);
        synchronized (this.callbacks) {
            this.callbacks.add(callback);
        }
    }

    @Override
    public void deregisterOnRemoveCallback(Consumer<HttpSession> callback) {
        synchronized (this.callbacks) {
            this.callbacks.remove(callback);
        }
    }
}
