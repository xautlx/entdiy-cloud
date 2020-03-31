package com.entdiy.mdm.dto.mapper;

import com.entdiy.common.model.BaseModelMapper;
import com.entdiy.mdm.dto.DepartmentEditDto;
import com.entdiy.mdm.entity.Department;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentEditDtoMapper extends BaseModelMapper<Department, DepartmentEditDto> {
}
