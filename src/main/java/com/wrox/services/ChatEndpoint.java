package com.wrox.services;

import com.wrox.entities.ChatSession;
import com.wrox.entities.CreateResult;
import com.wrox.entities.UserPrincipal;
import com.wrox.utils.ChatMessageCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.ScheduledFuture;

/**
 * 聊天终端
 *
 * Created by Dengbin on 2015/9/29.
 */
@ServerEndpoint(
        value = "/chat/{sessionId}",
        encoders = ChatMessageCodec.class,
        decoders = ChatMessageCodec.class,
        configurator = ChatEndpoint.EndpointConfigurator.class
)
public class ChatEndpoint {
    private static final Logger log = LogManager.getLogger();

    private boolean closed = false;
    private Session wsSession;
    private Session otherSession;
    private HttpSession httpSession;
    private ChatSession chatSession;
    private Principal principal;
    private ScheduledFuture<?> pingFuture;

    @Inject
    private SessionRegistry sessionRegistry;
    @Inject
    private ChatService chatService;
    @Inject
    private TaskScheduler taskScheduler;

//    private final Consumer<HttpSession> callback;

    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") long sessionId) {
        log.entry();
        this.httpSession = EndpointConfigurator.getExposedSession(session);
        this.principal = EndpointConfigurator.getExposedPrincipal(session);

        try {
            if (this.principal == null) {
                log.warn("聊天服务未授权。");
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "你没有登录！"));
                return;
            }

            if (sessionId < 1) {
                CreateResult result = this.chatService.createSession(this.principal.getName());
                this.chatSession = result.getChatSession();
                this.chatSession.setCustomer(session);
                this.chatSession.setOnRepresentativeJoin(s -> this.otherSession = s);
                session.getBasicRemote().sendObject(result.getCreateMessage());
            }
        } catch (IOException | EncodeException e) {

        } finally {
            log.exit();
        }
    }

    private void httpSessionRemoved(HttpSession httpSession) {
        if (httpSession == this.httpSession) {
            synchronized (this) {
                if (this.closed) {
                    return;
                }
                log.info("Chat session ended abruptly by {} logging out.");
            }
        }
    }

    public static class EndpointConfigurator extends SpringConfigurator {
        private static final String HTTP_SESSION_KEY = "com.wrox.ws.http.session";
        private static final String PRINCIPAL_KEY = "com.wrox.ws.user.principal";

        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            log.entry();
            super.modifyHandshake(sec, request, response);

            HttpSession httpSession = (HttpSession) request.getHttpSession();
            sec.getUserProperties().put(HTTP_SESSION_KEY, httpSession);
            sec.getUserProperties().put(PRINCIPAL_KEY, UserPrincipal.getPrincipal(httpSession));
            log.exit();
        }

        private static HttpSession getExposedSession(Session session) {
            return (HttpSession) session.getUserProperties().get(HTTP_SESSION_KEY);
        }

        private static Principal getExposedPrincipal(Session session) {
            return (Principal) session.getUserProperties().get(PRINCIPAL_KEY);
        }
    }
}
