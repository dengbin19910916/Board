package com.wrox.controllers.forms;

/**
 * 创建讨论表单，用户发起讨论，由主题和消息组成。
 *
 * @author dengb
 */
public class DiscussionForm {
    private String user;    // 用户（Email）
    private String subject; // 主题
    private String message; // 消息

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DiscussionForm{" +
                "user='" + user + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
