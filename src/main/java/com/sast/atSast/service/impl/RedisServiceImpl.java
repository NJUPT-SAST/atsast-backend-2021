package com.sast.atSast.service.impl;

import com.alibaba.fastjson.JSON;
import com.sast.atSast.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by
 *
 * @author Zero
 * @date 2020/5/2 0:27
 */
@Component
public class RedisServiceImpl implements RedisService {

    private static final long DEFAULT_TIMEOUT = 60;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;
    private final RedisTemplate<String, String> template;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, String> template) {
        this.template = template;
    }


    @Override
    public void setToCache(String key, String value) {
        setToCache(key, value, DEFAULT_TIMEOUT);
    }

    @Override
    public void setToCache(String key, String value, long timeout) {
        setToCache(key, value, timeout, DEFAULT_TIME_UNIT);
    }

    @Override
    public void setToCache(String key, String value, long timeout, TimeUnit timeUnit) {
        template.boundValueOps(key).set(value, timeout, timeUnit);
    }

    @Override
    public <T> void setToCache(String key, T value) {
        setToCache(key, value, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT);
    }

    @Override
    public <T> void setToCache(String key, T value, long timeout) {
        setToCache(key, value, timeout, DEFAULT_TIME_UNIT);
    }

    @Override
    public <T> void setToCache(String key, T value, long timeout, TimeUnit timeUnit) {
        String content = JSON.toJSONString(value);
        setToCache(key, content, timeout, timeUnit);
    }

    @Override
    public String getFromCache(String key) {
        return template.boundValueOps(key).get();
    }

    @Override
    public <T> T getFromCache(String key, Class<T> tClass) {
        String string = template.boundValueOps(key).get();
        if (StringUtils.isBlank(string)) {
            return null;
        }

        return JSON.parseObject(string, tClass);
    }

    @Override
    public void expandExpireTime(String key, long timeout, TimeUnit timeUnit) {
        template.boundValueOps(key).expire(timeout, timeUnit);
    }

    @Override
    public void removeFromCache(String key) {
        template.delete(key);
    }

    @Override
    public void removeFromCache(List<String> keys) {
        keys.forEach(this::removeFromCache);
    }

    @Override
    public Boolean setBit(String key, long offset, boolean value) {
        return template.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Boolean getBit(String key, long offset) {
        return template.opsForValue().getBit(key, offset);
    }

    @Override
    public <T> void pushHash(String key, String field, T value) {
        template.boundHashOps(key).put(field, value);
    }

    @Override
    public Object getHash(String key, String field) {
        return template.opsForHash().get(key, field);
    }

    @Override
    public Long incrHash(String key, String field) {
        return template.opsForHash().increment(key, field, 1);
    }

    @Override
    public boolean setIfAbsent(String key, String value, long timeout, TimeUnit timeUnit) {

        //利用jedis实现原子性操作
        RedisCallback<Boolean> sessionCallback = (connection) -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();

            String success = "";
            //NX setIfAbsent  EX seconds   PX ms
            switch (timeUnit) {
                case DAYS:
                    success = commands.set(key, value, "NX", "EX", timeout * 60 * 60 * 24);
                    break;
                case MINUTES:
                    success = commands.set(key, value, "NX", "EX", timeout * 60);
                    break;
                case SECONDS:
                    success = commands.set(key, value, "NX", "EX", timeout);
                    break;
                case HOURS:
                    success = commands.set(key, value, "NX", "EX", timeout * 60 * 60);
                    break;
                case MILLISECONDS:
                    success = commands.set(key, value, "NX", "PX", timeout);
            }

            return StringUtils.isNotEmpty(success);
        };

        return template.execute(sessionCallback);
    }
}
