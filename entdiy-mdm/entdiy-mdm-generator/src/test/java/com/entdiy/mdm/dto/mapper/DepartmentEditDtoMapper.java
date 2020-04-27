package com.entdiy.mdm.dto.mapper;

import com.entdiy.mdm.entity.Department;
import com.entdiy.mdm.dto.DepartmentEditDto;
import com.entdiy.common.model.BaseModelMapper;
import org.mapstruct.Mapper;

/**
 * <p>
 * 部门 EditDtoMapper
 * </p>
 *
 * @author Li Xia
 * @since 2020-04-27
 */
@Mapper
public interface DepartmentEditDtoMapper extends BaseModelMapper<Department, DepartmentEditDto> {

}
