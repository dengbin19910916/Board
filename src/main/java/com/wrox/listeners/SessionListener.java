package com.wrox.listeners;

import com.wrox.services.SessionRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by dengb on 2015/9/10.
 */
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener, ServletContextListener {

    private static final Logger log = LogManager.getLogger();

    @Inject
    private SessionRegistry sessionRegistry;

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        log.info("会话 ID {} 被改变为 {}。", oldSessionId, event.getSession().getId());
        this.sessionRegistry.updateSessionId(event.getSession(), oldSessionId);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("会话 {} 被创建。", se.getSession().getId());
        this.sessionRegistry.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("会话 {} 被销毁。", se.getSession().getId());
        this.sessionRegistry.removeSession(se.getSession());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        factory.initializeBean(this, "sessionListener");
        log.info("在Spring应用程序上下文中初始化会话侦听器。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
