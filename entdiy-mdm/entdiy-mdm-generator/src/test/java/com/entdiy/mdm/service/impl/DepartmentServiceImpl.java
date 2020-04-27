package com.entdiy.mdm.service.impl;

import com.entdiy.mdm.entity.Department;
import com.entdiy.mdm.mapper.DepartmentMapper;
import com.entdiy.mdm.service.IDepartmentService;
import com.entdiy.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.entdiy.mdm.dto.DepartmentEditDto;
import com.entdiy.mdm.dto.mapper.DepartmentEditDtoMapper;

import lombok.Getter;
import javax.annotation.Resource;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-04-27
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department, DepartmentEditDto> implements IDepartmentService {

    @Getter
    @Resource
    private DepartmentEditDtoMapper editDtoModelMapper;

}
