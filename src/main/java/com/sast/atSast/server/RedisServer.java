package com.sast.atSast.server;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/26/12:02
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
@NoArgsConstructor
public class RedisServer {
    private String host;
    private String port;
    private String password;
}
