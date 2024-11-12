package com.ltrha.ticket.config.minio;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinioProperties {
    private String endpoint;
    private Integer port;
    private String accessKey;
    private String secretKey;

    /**
     * //" If it is true, It uses https instead of http, The default value is true"
     */
    private boolean secure;
    private String bucketName;
    private long imageSize;
    private long fileSize;


}
