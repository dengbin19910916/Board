package com.wrox.repositories;

/**
 * Created by dengb on 2015/9/9.
 */
public interface UserRepository {

    /**
     * 根据用户名返回相应的密码。
     *
     * @param username 用户名
     * @return 密码
     */
    String getPasswordForUser(String username);
}
