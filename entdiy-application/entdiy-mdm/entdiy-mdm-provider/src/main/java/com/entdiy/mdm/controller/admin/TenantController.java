package com.entdiy.mdm.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.exception.ValidationException;
import com.entdiy.common.model.BatchResult;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.web.anna.MenuData;
import com.entdiy.mdm.entity.Tenant;
import com.entdiy.mdm.service.ITenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * <p>
 * 租户 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Api(tags = "租户管理")
@RestController
@RequestMapping("/mdm" + UriPrefix.ADMIN + "/tenant")
@MenuData(value = "/系统管理/租户管理",
        component = "/mdm/TenantList",
        entryUri = "/mdm" + UriPrefix.ADMIN + "/tenant" + "/query")
public class TenantController extends BaseRestController<ITenantService, Tenant> {

    @Getter
    @Autowired
    private ITenantService baseService;

    @Override
    @ApiOperation(value = "保存")
    @PostMapping(value = "/edit")
    public ViewResult<Tenant> editSave(@ApiParam("保存数据对象") @RequestBody @Validated Tenant editDto) {
        return ViewResult.success("数据保存已处理", super.entityEditSave(editDto, (dto, entity) -> {
            if (dto.isNotNew()
                    && entity.getTenantCode().equals(BaseConstant.ROOT)
                    && !dto.getTenantCode().equals(entity.getTenantCode())) {
                throw new ValidationException(BaseConstant.ROOT + "租户代码不可修改");
            }
        }));
    }

    @Override
    @ApiOperation(value = "列表")
    @GetMapping(value = "/query")
    public ViewResult<Page<Tenant>> query(@ApiParam("分页参数对象") Pageable pageable,
                                          @ApiParam("查询参数对象") QueryWrapper<Tenant> queryWrapper) {
        queryWrapper.ne("tenant_code", BaseConstant.ROOT);
        return ViewResult.success(super.entityQuery(pageable, queryWrapper));
    }

    @Override
    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ViewResult<BatchResult<Serializable>> delete(Long[] ids) {
        //系统内置超级管理租户不可删除
        return ViewResult.success("数据删除已处理", super.entityDelete(ids, entity -> !BaseConstant.ROOT.equals(entity.getTenantCode()))).skipMessage(true);
    }
}
