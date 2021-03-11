package com.entdiy.mdm.controller.admin;


import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.web.anna.MenuData;
import com.entdiy.common.web.json.JsonViews;
import com.entdiy.mdm.entity.Menu;
import com.entdiy.mdm.service.IMenuService;
import com.fasterxml.jackson.annotation.JsonView;
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
 * 菜单 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/mdm" + UriPrefix.ADMIN + "/menu")
@MenuData(value = "/系统管理/菜单管理",
        component = "/mdm/MenuList",
        entryUri = "/mdm" + UriPrefix.ADMIN + "/menu/query")
public class MenuController extends BaseRestController<IMenuService, Menu> {

    @Getter
    @Autowired
    private IMenuService baseService;

    @ApiOperation(value = "列表数据")
    @GetMapping(value = "/list")
    @JsonView({JsonViews.AdminReadOnly.class})
    public ViewResult<List<Menu>> list() {
        return ViewResult.success(baseService.findAllCacheable());
    }
}
