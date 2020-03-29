package com.entdiy.mdm.controller.admin;


import com.entdiy.common.controller.BaseRestController;
import com.entdiy.common.model.ViewResult;
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
@RequestMapping("/mdm/department")
public class DepartmentController extends BaseRestController {
    @GetMapping("/ping")
    public ViewResult<String> ping() {
        return ViewResult.success("Pong");
    }

}
