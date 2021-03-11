package com.entdiy.mdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.entdiy.common", "com.entdiy.support", "com.entdiy.mdm"})
@EntityScan(basePackages = {"com.entdiy.mdm.entity"})
@MapperScan({"com.entdiy.common.mapper", "com.entdiy.mdm.mapper"})
public class MdmTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdmTestApplication.class, args);
    }

}
