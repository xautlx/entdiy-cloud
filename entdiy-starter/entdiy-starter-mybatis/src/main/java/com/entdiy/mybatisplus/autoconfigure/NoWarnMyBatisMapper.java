package com.entdiy.mybatisplus.autoconfigure;

import org.apache.ibatis.annotations.Mapper;

/**
 * 仅用于抑制 No MyBatis mapper was found in 警告日志
 */
@Mapper
public interface NoWarnMyBatisMapper {
}
