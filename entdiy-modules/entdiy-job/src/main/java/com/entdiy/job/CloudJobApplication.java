package com.entdiy.job;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 定时任务
 *
 *
 */
@SpringCloudApplication
public class CloudJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudJobApplication.class, args);
    }
}
