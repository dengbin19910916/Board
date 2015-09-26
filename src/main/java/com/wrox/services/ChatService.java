package com.wrox.services;

import com.wrox.entities.ChatMessage;
import com.wrox.entities.ChatSession;
import com.wrox.entities.CreateResult;
import com.wrox.entities.JoinResult;

import java.util.List;

/**
 * Created by dengb on 2015/9/11.
 */
public interface ChatService {

    CreateResult createSession(String user);

    JoinResult joinSession(long id, String user);

    ChatMessage leaveSession(ChatSession session, String user,
                             ReasonForLeaving reason);

    void logMessage(ChatSession session, ChatMessage message);

    List<ChatSession> getPendingSessions();

    enum ReasonForLeaving {
        NORMAL,

        LOGGED_OUT,

        ERROR
    }
}
