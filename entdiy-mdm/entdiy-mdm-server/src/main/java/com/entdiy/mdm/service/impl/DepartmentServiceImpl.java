package com.entdiy.mdm.service.impl;

import com.entdiy.common.service.BaseServiceImpl;
import com.entdiy.mdm.dto.DepartmentEditDto;
import com.entdiy.mdm.dto.mapper.DepartmentEditDtoMapper;
import com.entdiy.mdm.dto.mapper.DepartmentListDtoMapper;
import com.entdiy.mdm.entity.Department;
import com.entdiy.mdm.mapper.DepartmentMapper;
import com.entdiy.mdm.service.IDepartmentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-03-20
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department, DepartmentEditDto> implements IDepartmentService {

    @Getter
    @Autowired
    private DepartmentEditDtoMapper editDtoModelMapper;

    @Getter
    @Autowired
    private DepartmentListDtoMapper listDtoModelMapper;

}
