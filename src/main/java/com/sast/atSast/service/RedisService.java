package com.sast.atSast.service;


import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by
 *
 * @author Zero
 * @date 2020/5/2 0:27
 */
public interface RedisService {

    void setToCache(String key, String value);

    void setToCache(String key, String value, long timeout);

    void setToCache(String key, String value, long timeout, TimeUnit timeUnit);

    <T> void setToCache(String key, T value);

    <T> void setToCache(String key, T value, long timeout);

    <T> void setToCache(String key, T value, long timeout, TimeUnit timeUnit);

    String getFromCache(String key);

    <T> T getFromCache(String key, Class<T> tClass);

    void expandExpireTime(String key, long timeout, TimeUnit timeUnit);

    void removeFromCache(String key);

    void removeFromCache(List<String> keys);

    Boolean setBit(String key, long offset, boolean value);

    Boolean getBit(String key, long offset);

    <T> void pushHash(String key, String field, T value);

    Object getHash(String key, String field);

    Long incrHash(String key, String field);

    boolean setIfAbsent(String key, String value, long timeout, TimeUnit timeUnit);
}
