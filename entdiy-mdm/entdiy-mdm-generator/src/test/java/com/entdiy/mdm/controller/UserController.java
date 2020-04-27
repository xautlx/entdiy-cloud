package com.entdiy.mdm.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.mdm.service.IUserService;
import com.entdiy.mdm.dto.mapper.UserEditDtoMapper;
import com.entdiy.mdm.dto.mapper.UserListDtoMapper;

import lombok.Getter;
import javax.annotation.Resource;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Li Xia
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/mdm/user")
public class UserController extends BaseRestController {

    @Getter
    @Resource
    private IUserService baseService;

    @Getter
    @Resource
    private UserEditDtoMapper editDtoModelMapper;

    @Getter
    @Resource
    private UserListDtoMapper listDtoModelMapper;

}
