package com.wrox.entities;

import java.time.Instant;

public class Reply {
    /**
     * 序号
     */
    private long id;
    /**
     * 讨论序号
     */
    private long discussionId;
    /**
     * 回复的用户名（邮箱）
     */
    private String user;
    /**
     * 回复的内容
     */
    private String message;
    /**
     * 回复创建时间
     */
    private Instant created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", discussionId=" + discussionId +
                ", user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", created=" + created +
                '}';
    }
}
