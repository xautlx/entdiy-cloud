package com.entdiy.mdm.dto.mapper;

import com.entdiy.mdm.entity.User;
import com.entdiy.mdm.dto.UserListDto;
import com.entdiy.common.model.BaseModelMapper;
import org.mapstruct.Mapper;

/**
* <p>
 * 用户 ListDtoMapper
 * </p>
*
* @author Li Xia
* @since 2020-04-27
*/
@Mapper
public interface UserListDtoMapper extends BaseModelMapper<User, UserListDto> {

}
