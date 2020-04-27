package com.entdiy.mdm.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.mdm.service.IDepartmentService;
import com.entdiy.mdm.dto.mapper.DepartmentEditDtoMapper;
import com.entdiy.mdm.dto.mapper.DepartmentListDtoMapper;

import lombok.Getter;
import javax.annotation.Resource;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/mdm/department")
public class DepartmentController extends BaseRestController {

    @Getter
    @Resource
    private IDepartmentService baseService;

    @Getter
    @Resource
    private DepartmentEditDtoMapper editDtoModelMapper;

    @Getter
    @Resource
    private DepartmentListDtoMapper listDtoModelMapper;

}
