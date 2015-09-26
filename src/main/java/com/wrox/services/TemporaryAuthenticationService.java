package com.wrox.services;

import com.wrox.entities.UserPrincipal;
import com.wrox.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.security.Principal;

/**
 * Created by dengb on 2015/9/8.
 */
@Service
public class TemporaryAuthenticationService implements AuthenticationService {

    private static final Logger log = LogManager.getLogger();

    @Inject
    private UserRepository userRepository;

    @Override
    public Principal authenticate(String username, String password) {
        String currentPassword = this.userRepository.getPasswordForUser(username);
        if (currentPassword == null) {
            log.warn("身份验证失败，不存在此用户{}。", username);
            return null;
        }

        if (!currentPassword.equals(password)) {
            log.warn("用户{}身份验证失败。", username);
            return null;
        }
        
        log.debug("用户{}验证身份成功。");

        return new UserPrincipal(username);
    }
}
