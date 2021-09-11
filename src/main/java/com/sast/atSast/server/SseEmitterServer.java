package com.sast.atSast.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class SseEmitterServer {
    private static final Logger logger = LoggerFactory.getLogger(SseEmitterServer.class);

    /**
     * 当前连接数
     */
    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 使用map对象，便于根据uid来获取对应的SseEmitter，或者放redis里面
     */
    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 创建用户连接并返回 SseEmitter
     * @param uid 用户ID
     * @return SseEmitter
     */
    public static SseEmitter connect(String uid) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(uid));
        sseEmitter.onError(errorCallBack(uid));
        sseEmitter.onTimeout(timeoutCallBack(uid));
        sseEmitterMap.put(uid, sseEmitter);
        // 数量+1
        count.getAndIncrement();
        logger.info("创建新的sse连接，当前用户：{}", uid);
        return sseEmitter;
    }

    /**
     * 给指定用户发送信息
     */
    public static void sendMessage(String uid, String message) {
        if (sseEmitterMap.containsKey(uid)) {
            try {
                // sseEmitterMap.get(uid).send(message, MediaType.APPLICATION_JSON);
                sseEmitterMap.get(uid).send(message);
            } catch (IOException e) {
                logger.error("用户[{}]推送异常:{}", uid, e.getMessage());
                removeUser(uid);
            }
        }
    }

    /**
     * 移除用户连接
     */
    public static void removeUser(String uid) {
        sseEmitterMap.remove(uid);
        // 数量-1
        count.getAndDecrement();
        logger.info("移除用户：{}", uid);
    }

    /**
     * 获取当前连接信息
     */
    public static List<String> getIds() {
        return new ArrayList<>(sseEmitterMap.keySet());
    }

    /**
     * 获取当前连接数量
     */
    public static int getUserCount() {
        return count.intValue();
    }

    private static Runnable completionCallBack(String uid) {
        return () -> {
            logger.info("结束连接：{}", uid);
            removeUser(uid);
        };
    }

    private static Runnable timeoutCallBack(String uid) {
        return () -> {
            logger.info("连接超时：{}", uid);
            removeUser(uid);
        };
    }

    private static Consumer<Throwable> errorCallBack(String uid) {
        return throwable -> {
            logger.info("连接异常：{}", uid);
            removeUser(uid);
        };
    }

}

