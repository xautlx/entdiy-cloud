package com.entdiy.mdm.controller.admin;


import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.model.ViewResult;
import com.entdiy.mdm.entity.User;
import com.entdiy.mdm.service.IMenuService;
import com.entdiy.mdm.service.IUserService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/mdm/admin/user")
public class UserController extends BaseRestController<IUserService, User> {

    @Getter
    @Autowired
    private IUserService baseService;

    @Autowired
    private IMenuService iMenuService;

    @ApiOperation(value = "查询用户菜单、权限等数据", hidden = true)
    @GetMapping(value = "/menu-permission-data")
    public ViewResult<Map<String, Object>> menuPermissionData() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("menus", iMenuService.findAllCacheable());
        return ViewResult.success(data);
    }
}
