package com.entdiy.file.config;

import com.entdiy.file.service.ISysFileService;
import com.entdiy.file.service.MinioSysFileServiceImpl;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 *
 * 
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
@ConditionalOnProperty(prefix = "minio", name = "enable")
public class MinioConfig {
    /**
     * 服务地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucketName;

    @Bean
    public ISysFileService sysFileService() {
        return new MinioSysFileServiceImpl();
    }

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }
}
