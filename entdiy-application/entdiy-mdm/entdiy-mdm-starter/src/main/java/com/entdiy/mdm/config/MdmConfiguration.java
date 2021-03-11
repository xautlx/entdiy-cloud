package com.entdiy.mdm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.entdiy.common", "com.entdiy.mdm"})
@EntityScan(basePackages = {"com.entdiy.mdm.entity"})
@MapperScan({"com.entdiy.common.mapper", "com.entdiy.mdm.mapper"})
public class MdmConfiguration {

}
