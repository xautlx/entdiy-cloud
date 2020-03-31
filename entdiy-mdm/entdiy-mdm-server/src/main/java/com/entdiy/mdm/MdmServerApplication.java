package com.entdiy.mdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.entdiy.mdm.mapper")
public class MdmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdmServerApplication.class, args);
    }

}
