package com.entdiy.common.mapper;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.entity.LogicDeletePersistable;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

@Slf4j
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start mybatis plus insert fill ....");
        AuthDataHolder.AuthData authData = AuthDataHolder.get();
        this.strictInsertFill(metaObject, BaseEntity.CREATED_AUDIT_DATA_COLUMN_NAME, String.class, "{CREATED:TODO}");
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
        this.strictInsertFill(metaObject, LogicDeletePersistable.LOGIC_DELETED_COLUMN_NAME, Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start mybatis plus update fill ....");
        AuthDataHolder.AuthData authData = AuthDataHolder.get();
        this.strictInsertFill(metaObject, BaseEntity.UPDATED_AUDIT_DATA_COLUMN_NAME, String.class, "{UPDATED:TODO}");
    }
}
