package com.wrox.entities;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.security.Principal;

/**
 * 用户主体，确定用户身份。
 */
public class UserPrincipal implements Principal, Cloneable, Serializable {
    private static final long serialVersionUID = -7004762602062662863L;

    private final String username;

    public UserPrincipal(String username) {
        this.username = username;
    }

    /**
     * 返回用户主体信息。
     *
     * @param session 会话对象
     * @return 主体对象
     */
    public static Principal getPrincipal(HttpSession session) {
        return session == null ? null : (Principal) session.getAttribute("com.wrox.user.principal");
    }

    /**
     * 设置用户主体信息。
     *
     * @param session 会话对象
     * @param principal 主体对象
     */
    public static void setPrincipal(HttpSession session, Principal principal) {
        session.setAttribute("com.wrox.user.principal", principal);
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UserPrincipal && ((UserPrincipal) other).username.equals(this.username);
    }

    @Override
    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    protected UserPrincipal clone() {
        try {
            return (UserPrincipal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // not possible
        }
    }

    @Override
    public String toString() {
        return this.username;
    }
}
