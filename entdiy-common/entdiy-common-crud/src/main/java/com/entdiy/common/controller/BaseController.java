package com.entdiy.common.controller;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.model.BaseModelMapper;
import com.entdiy.common.service.BaseService;

public abstract class BaseController<M extends BaseService<T>, T extends BaseEntity>{


    protected Class<?> entityClass = currentModelClass();

    private Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    public abstract M getBaseService();

    public abstract BaseModelMapper getEditDtoModelMapper();

    public abstract BaseModelMapper getListDtoModelMapper();
}
