package com.entdiy.common.controller;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.entdiy.common.dto.BaseEditDto;
import com.entdiy.common.dto.BaseListDto;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.model.BaseModelMapper;
import com.entdiy.common.service.BaseService;
import lombok.Getter;

public abstract class BaseController<
        M extends BaseService<T,EDTO>,
        T extends BaseEntity,
        EDTO extends BaseEditDto,
        LDTO extends BaseListDto> {

    @Getter
    protected Class<?> entityClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);

    public abstract M getBaseService();

    public abstract BaseModelMapper getEditDtoModelMapper();

    public abstract BaseModelMapper getListDtoModelMapper();
}
