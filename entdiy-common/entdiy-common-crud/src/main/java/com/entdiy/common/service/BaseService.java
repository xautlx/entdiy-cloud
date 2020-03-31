package com.entdiy.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entdiy.common.dto.BaseEditDto;
import com.entdiy.common.entity.BaseEntity;

public interface BaseService<T extends BaseEntity, EDTO extends BaseEditDto> extends IService<T> {

    /**
     * 基于编辑DTO对象保存数据
     *
     * @param edto 编辑DTO对象
     */
    boolean saveOrUpdateDTO(EDTO edto);
}
