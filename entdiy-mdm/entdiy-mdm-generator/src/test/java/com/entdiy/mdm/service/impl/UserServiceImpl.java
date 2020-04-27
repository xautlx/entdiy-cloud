package com.entdiy.mdm.service.impl;

import com.entdiy.mdm.entity.User;
import com.entdiy.mdm.mapper.UserMapper;
import com.entdiy.mdm.service.IUserService;
import com.entdiy.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.entdiy.mdm.dto.UserEditDto;
import com.entdiy.mdm.dto.mapper.UserEditDtoMapper;

import lombok.Getter;
import javax.annotation.Resource;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-04-27
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserEditDto> implements IUserService {

    @Getter
    @Resource
    private UserEditDtoMapper editDtoModelMapper;

}
