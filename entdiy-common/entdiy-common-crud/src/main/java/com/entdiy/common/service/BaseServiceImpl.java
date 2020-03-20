package com.entdiy.common.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.mapper.BaseMyBatisMapper;
import org.modelmapper.ModelMapper;

import javax.annotation.Resource;

public class BaseServiceImpl<M extends BaseMyBatisMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Resource
    protected ModelMapper modelMapper;


}
