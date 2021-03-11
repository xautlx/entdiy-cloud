package com.entdiy.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entdiy.common.entity.BaseIdVersionEntity;
import com.entdiy.common.model.BatchResult;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.web.json.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * 设计原则说明：出于如下设计考虑，本框架遵循Controller层面避免直接返回Entity方式，而是采用做必要的DTO转换后输出JSON响应。
 * 1，属性阻抗：一般UI界面提交参数和Entity对象属性存在差异化逻辑处理，列表界面属性列表存在多表Join或计算属性等和Entity属性很难直接映射，因此有必要定义DTO进行转换组合处理
 * 2，如果直接Entity作为Web层方法输入参数或返回参数，会导致诸如Swagger这样的API文档输出污染，输出过多API属性或者各粒度API接口输出相同且含义不明确的文档
 * 3，如果直接以Entity作为返回参数JSON序列化，虽然可以借助Jackson JsonView等注解来支持不同视同渲染输出，但是其定义也比较繁琐，还不如在DTO中定义属性简洁明了
 *
 * 基类提供基础CURD API接口方法，如果需要屏蔽子类可覆写直接抛出异常：throw new UnsupportedOperationException();
 */
public abstract class BaseRestController<
        M extends BaseService<T>,
        T extends BaseIdVersionEntity> extends BaseController<M, T> {

    @ApiOperation(value = "查看")
    @GetMapping(value = "/edit")
    @JsonView(JsonViews.AdminReadWrite.class)
    public ViewResult<T> editShow(@ApiParam("操作对象主键") Long id) {
        return ViewResult.success(super.entityEditShow(id));
    }

    @ApiOperation(value = "保存")
    @PostMapping(value = "/edit")
    @JsonView(JsonViews.AdminReadWrite.class)
    public ViewResult<T> editSave(@ApiParam("保存数据对象") @RequestBody @Validated T editDto) {
        return ViewResult.success("数据保存已处理", super.entityEditSave(editDto));
    }

    @ApiOperation(value = "列表")
    @GetMapping(value = "/query")
    @JsonView(JsonViews.AdminReadOnly.class)
    public ViewResult<Page<T>> query(@ApiParam("分页参数对象") Pageable pageable,
                                     @ApiParam("查询参数对象") QueryWrapper<T> queryWrapper) {
        return ViewResult.success(super.entityQuery(pageable, queryWrapper));
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    @JsonView(JsonViews.AdminReadOnly.class)
    public ViewResult<BatchResult<Serializable>> delete(@ApiParam("操作对象主键数组") @RequestParam("id") Long[] ids) {
        return ViewResult.success("数据删除已处理", super.entityDelete(ids)).skipMessage(true);
    }
}
