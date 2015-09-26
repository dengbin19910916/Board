package com.wrox.services;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by dengb on 2015/9/10.
 */
public interface SessionRegistry {

    void addSession(HttpSession session);

    void updateSessionId(HttpSession session, String oldSessionId);

    void removeSession(HttpSession session);

    List<HttpSession> getAllSessions();

    int getNumberOfSessions();

    void registerOnRemoveCallback(Consumer<HttpSession> callback);

    void deregisterOnRemoveCallback(Consumer<HttpSession> callback);
}
