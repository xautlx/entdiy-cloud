package com.entdiy.mdm.controller.admin;


import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.mdm.entity.Department;
import com.entdiy.mdm.service.IDepartmentService;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/mdm/admin/department")
public class DepartmentController extends BaseRestController<IDepartmentService, Department> {

    @Getter
    @Autowired
    private IDepartmentService baseService;

}
