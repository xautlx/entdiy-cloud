package com.entdiy.common.controller;

import com.entdiy.common.dto.BaseEditDto;
import com.entdiy.common.dto.BaseListDto;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.service.BaseService;

public abstract class BaseRestController<
        M extends BaseService<T>,
        T extends BaseEntity,
        EDTO extends BaseEditDto,
        LDTO extends BaseListDto> extends BaseController {



}
