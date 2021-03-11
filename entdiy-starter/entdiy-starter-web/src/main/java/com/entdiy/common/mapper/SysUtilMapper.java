package com.entdiy.common.mapper;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.entdiy.common.model.DuplicateCheckVo;

public interface SysUtilMapper extends Mapper {
    Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);
}
