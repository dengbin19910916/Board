package com.wrox.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class Discussion {
    /**
     * 序号
     */
    private long id;
    /**
     * 创建讨论的用户名（Email）
     */
    private String user;
    /**
     * 讨论的主题
     */
    private String subject;
    /**
     * URI，处理主题得来
     */
    private String uriSafeSubject;
    /**
     * 讨论的内容
     */
    private String message;
    /**
     * 创建时间
     */
    private Instant created;
    /**
     * 最后回复时间
     */
    private Instant lastUpdated;
    /**
     * 参与过回复的用户
     */
    private Set<String> subscribedUsers = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUriSafeSubject() {
        return uriSafeSubject;
    }

    public void setUriSafeSubject(String uriSafeSubject) {
        this.uriSafeSubject = uriSafeSubject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<String> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(Set<String> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", subject='" + subject + '\'' +
                ", uriSafeSubject='" + uriSafeSubject + '\'' +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                ", subscribedUsers=" + subscribedUsers +
                '}';
    }
}
