package com.entdiy.mdm.service.impl;

import com.entdiy.mdm.entity.User;
import com.entdiy.mdm.mapper.UserMapper;
import com.entdiy.mdm.service.IUserService;
import com.entdiy.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-03-20
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

}
