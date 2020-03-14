package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuemei.common.BaseConstant;
import com.xuemei.common.dto.EntityBatchResult;
import ${superControllerClassPackage};
import com.xuemei.common.web.annotation.PageQueryApiImplicitParams;
import ${package.Dto}.${entity}EditDto;
import ${package.Dto}.${entity}ListDto;
import ${package.Dto}.${entity}SearchDto;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
</#if>

/**
 * <p>
 * ${table.comment?replace("表","")}前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping(BaseConstant.RESTFUL_PREFIX + "/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Api(tags = "${table.comment?replace("表","")}接口")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${table.serviceName}, ${entity}> {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @Override
    public ${table.serviceName} getBaseService() {
        return ${table.serviceName?uncap_first};
    }


    @ApiOperation(value = "获取对象", notes = "如果业务需求在新增对象时初始化部分属性，新增界面显示需要以主键为空调用此接口")
    @ResponseBody
    @GetMapping(value = "/edit")
    public ${entity}EditDto editShow(@RequestParam(value = "kid", required = false) String kid) {
        return super.editShow(${entity}EditDto.class, kid);
    }

    @ApiOperation(value = "保存对象", notes = "返回结果为保存后最新数据")
    @ResponseBody
    @PostMapping(value = "/edit")
    public ${entity}EditDto editSave(@RequestBody ${entity}EditDto dto) {
        return super.editSave(dto);
    }

    @ApiOperation(value = "批量保存对象", notes = "参数为包含kid及其他必要修改属性值的DTO对象数组")
    @ResponseBody
    @PostMapping(value = "/edit/batch")
    public EntityBatchResult edit(@RequestBody List<${entity}EditDto> dtoList) {
        return super.editSaveBatch(dtoList);
    }

    @ApiOperation(value = "批量删除", notes = "参数为包含kid属性值的DTO对象数组")
    @ResponseBody
    @PostMapping(value = "/delete")
    public EntityBatchResult delete(@RequestBody List<${entity}EditDto> dtoList) {
        return super.deleteBatch(dtoList);
    }

    /**
     * 动态参数分页查询接口
     *
     * @param page         分页查询参数
     * @param queryWrapper 动态查询参数
     * @return 分页数据对象
     */
    @ApiOperation(value = "动态参数分页查询")
    @PageQueryApiImplicitParams
    @ResponseBody
    @GetMapping(value = "/search")
    public IPage<${entity}ListDto> list(
            ${entity}SearchDto searchDto,
            @ApiParam("导出标识：默认不设置为普通查询；如果设置为true则实现export导出数据功能，默认导出最大记录数1000")
            @RequestParam(name = "export", required = false) Boolean export,
            @ApiIgnore Page page,
            @ApiIgnore QueryWrapper<${entity}> queryWrapper,
            HttpServletResponse response
    ) {
        // 可根据业务需要追加额外过滤条件如只能查询当前用户创建数据，防止用户越权访问数据。
        //queryWrapper.eq("submitUser",AuthDataHolder.get().getUserId());
        // 根据业务需要和数据索引等设计考虑，对前端查询参数转换为相关查询条件
        //if (StringUtils.isNotBlank(searchDto.getName())) {
        //    queryWrapper.like("name", searchDto.getName());
        //}
        return super.list(${entity}ListDto.class, searchDto, export, page, queryWrapper, response);
    }
}
</#if>
