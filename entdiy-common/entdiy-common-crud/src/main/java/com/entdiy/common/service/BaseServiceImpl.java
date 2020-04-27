package com.entdiy.common.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entdiy.common.dto.BaseEditDto;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.mapper.BaseMyBatisMapper;
import com.entdiy.common.model.BaseModelMapper;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseServiceImpl<
        M extends BaseMyBatisMapper<T>,
        T extends BaseEntity,
        EDTO extends BaseEditDto> extends ServiceImpl<M, T> implements BaseService<T, EDTO> {

    public abstract BaseModelMapper getEditDtoModelMapper();

    @Transactional(rollbackFor = Exception.class)
    @Override
    @SneakyThrows
    public boolean saveOrUpdateDTO(BaseEditDto edto) {
        T entity;
        if (edto.isNew()) {
            entity = (T) entityClass.getDeclaredConstructor().newInstance();
        } else {
            entity = this.getById(edto.getId());
        }
        getEditDtoModelMapper().mergeDtoToEntity(edto, entity);

        boolean result = this.saveOrUpdate(entity);
        edto.setId(entity.getId());
        edto.setVersion(entity.getVersion());

        return result;
    }
}
