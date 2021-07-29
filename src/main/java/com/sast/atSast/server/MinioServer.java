package com.sast.atSast.server;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/19/21:44
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "minio")
@Data
@NoArgsConstructor
public class MinioServer {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String port;
    private String bucketImageName;
}
