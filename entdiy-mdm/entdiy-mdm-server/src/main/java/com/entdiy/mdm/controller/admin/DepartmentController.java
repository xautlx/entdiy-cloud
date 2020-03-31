package com.entdiy.mdm.controller.admin;


import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.model.ViewResult;
import com.entdiy.common.web.databind.annotation.EditDtoDataBinder;
import com.entdiy.common.web.swagger.PrimaryKeyApiImplicitParams;
import com.entdiy.mdm.dto.DepartmentEditDto;
import com.entdiy.mdm.dto.DepartmentListDto;
import com.entdiy.mdm.dto.mapper.DepartmentEditDtoMapper;
import com.entdiy.mdm.dto.mapper.DepartmentListDtoMapper;
import com.entdiy.mdm.entity.Department;
import com.entdiy.mdm.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-03-20
 */
@RestController
@RequestMapping("/admin/department")
public class DepartmentController extends BaseRestController<IDepartmentService, Department, DepartmentEditDto, DepartmentListDto> {

    @Getter
    @Autowired
    private IDepartmentService baseService;

    @Getter
    @Autowired
    private DepartmentEditDtoMapper editDtoModelMapper;

    @Getter
    @Autowired
    private DepartmentListDtoMapper listDtoModelMapper;

    @GetMapping("/ping")
    public ViewResult<String> ping() {
        return ViewResult.success("Pong");
    }

    @ApiOperation(value = "获取对象", notes = "如果业务需求在新增对象时初始化部分属性，新增界面显示需要以主键为空调用此接口")
    @GetMapping(value = "/edit")
    @PrimaryKeyApiImplicitParams
    public ViewResult<DepartmentEditDto> editShow(@ApiIgnore @EditDtoDataBinder DepartmentEditDto dto) {
        return ViewResult.success(dto);
    }

    @ApiOperation(value = "保存对象", notes = "返回结果为保存后最新数据")
    @PostMapping(value = "/edit")
    public ViewResult<DepartmentEditDto> editSave(@RequestBody DepartmentEditDto dto) {
        baseService.saveOrUpdateDTO(dto);
        return ViewResult.success(dto);
    }
}
