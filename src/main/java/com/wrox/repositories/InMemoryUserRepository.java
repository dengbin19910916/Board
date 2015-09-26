package com.wrox.repositories;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dengb on 2015/9/9.
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, String> userDatabase = new ConcurrentHashMap<>();

    public InMemoryUserRepository() {
        this.userDatabase.put("Nicholas", "password");
        this.userDatabase.put("Sarah", "drowssap");
        this.userDatabase.put("Mike", "wordpass");
        this.userDatabase.put("John", "green");
        this.userDatabase.put("Dengbin", "123");
    }

    @Override
    public String getPasswordForUser(String username) {
        return this.userDatabase.get(username);
    }
}
