package com.entdiy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 整合单一运行服务
 */
@SpringCloudApplication
public class CloudAllinoneApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudAllinoneApplication.class, args);
    }
}
