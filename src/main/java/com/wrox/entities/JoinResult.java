package com.wrox.entities;

/**
 * Created by dengb on 2015/9/11.
 */
public class JoinResult {

    private final ChatSession chatSession;
    private final ChatMessage chatMessage;

    public JoinResult(ChatSession chatSession, ChatMessage chatMessage) {
        this.chatSession = chatSession;
        this.chatMessage = chatMessage;
    }

    public ChatSession getChatSession() {
        return chatSession;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
