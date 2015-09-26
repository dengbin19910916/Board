package com.wrox.services;

import java.security.Principal;

/**
 * Created by dengb on 2015/9/8.
 */
public interface AuthenticationService {

    /**
     * 鉴定用户的真实性。
     *
     * @param username 用户名
     * @param password 密码
     * @return Principal对象
     */
    Principal authenticate(String username, String password);
}
