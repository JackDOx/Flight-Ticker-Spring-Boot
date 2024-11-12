package com.ltrha.ticket.config.minio;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

//    private final MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient(MinioProperties minioProperties) {
        return MinioClient.builder()
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
//                .endpoint(minioProperties.getEndpoint(), minioProperties.getPort(), minioProperties.isSecure())
                .endpoint(minioProperties.getEndpoint())
                .build();
    }

    @Bean
    public MinioProperties minioProperties() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("secret/minio.yml"));
        Properties properties = yaml.getObject();

        if (properties == null) {
            throw new IllegalArgumentException("minio.yml file not found");
        }

        return MinioProperties.builder()
                .endpoint(properties.getProperty("minio.endpoint"))
                .port(Integer.parseInt(properties.getProperty("minio.port")))
                .accessKey(properties.getProperty("minio.accessKey"))
                .secretKey(properties.getProperty("minio.secretKey"))
                .secure(Boolean.parseBoolean(properties.getProperty("minio.secure")))
                .bucketName(properties.getProperty("minio.bucket-name"))
                .imageSize(Long.parseLong(properties.getProperty("minio.image-size")))
                .fileSize(Long.parseLong(properties.getProperty("minio.file-size")))
                .build();

    }
}
