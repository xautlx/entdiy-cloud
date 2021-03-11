package com.entdiy.common.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface BaseMyBatisMapper<T> extends BaseMapper<T> {

    /**
     * 支持自动填充属性处理的根据 entity 逻辑删除。
     * 特别注意：只有调用此方法传入entity对象进行删除才会做自动填充属性处理，其余按照id等删除接口不会自动做属性填充处理
     *
     * @param entity 实体对象
     * @see MybatisMetaObjectHandler
     */
    int deleteByIdWithFill(T entity);


    @Update("UPDATE ${tableName} " +
            "SET parent_id_path= replace(parent_id_path,'${oldParentIdPathPrefix}','${newParentIdPathPrefix}') " +
            "${ew.customSqlSegment}")
    boolean cascadeUpdateChildrenParentIdPath(@Param(Constants.WRAPPER) Wrapper<T> updateWrapper,
                                              @Param("tableName") String tableName,
                                              @Param("oldParentIdPathPrefix") String oldParentIdPathPrefix,
                                              @Param("newParentIdPathPrefix") String newParentIdPathPrefix);

}
