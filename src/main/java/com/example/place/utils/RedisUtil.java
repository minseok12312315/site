package com.example.place.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import org.springframework.beans.factory.annotation.Value;

@Component
public class RedisUtil {

    private final Jedis jedis;
    private String host;
    private int port;

    public RedisUtil(@Value("${redis.host}") String host, @Value("${redis.port}") int port) {
        this.host = host;
        this.port = port;
        jedis = new Jedis(host, port);
    }

    public void setData(String key, String value) {
        jedis.set(key, value);
    }

    public String getData(String key) {
        return jedis.get(key);
    }
}

