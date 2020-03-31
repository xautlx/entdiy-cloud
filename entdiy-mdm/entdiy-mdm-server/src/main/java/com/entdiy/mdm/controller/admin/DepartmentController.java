package com.entdiy.mdm.controller.admin;


import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.model.ViewResult;
import com.entdiy.mdm.dto.DepartmentEditDto;
import com.entdiy.mdm.dto.mapper.DepartmentEditDtoMapper;
import com.entdiy.mdm.dto.mapper.DepartmentListDtoMapper;
import com.entdiy.mdm.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class DepartmentController extends BaseRestController {

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
    public DepartmentEditDto editShow(DepartmentEditDto dto) {
        return dto;
    }
}
