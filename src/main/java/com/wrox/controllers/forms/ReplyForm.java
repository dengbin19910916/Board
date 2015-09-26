package com.wrox.controllers.forms;

/**
 * 讨论回复表单。用户回复某一个讨论。
 *
 * @author dengb
 */
public class ReplyForm {
    private String user;
    private String message;

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

    @Override
    public String toString() {
        return "ReplyForm{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
