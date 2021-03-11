package com.entdiy.mdm.controller.admin;


import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.web.anna.MenuData;
import com.entdiy.mdm.entity.Permission;
import com.entdiy.mdm.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-22
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/mdm" + UriPrefix.ADMIN + "/permission")
@MenuData(value = "/系统管理/权限管理",
        component = "/mdm/PermissionList",
        entryUri = "/mdm" + UriPrefix.ADMIN + "/permission/query")
public class PermissionController extends BaseRestController<IPermissionService, Permission> {

    @Getter
    @Autowired
    private IPermissionService baseService;

    @ApiOperation(value = "列表数据")
    @GetMapping(value = "/list")
    public ViewResult<List<Permission>> list() {
        return ViewResult.success(baseService.findAllCacheable());
    }

}
