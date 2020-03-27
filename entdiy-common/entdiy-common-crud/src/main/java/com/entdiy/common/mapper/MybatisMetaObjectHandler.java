package com.entdiy.common.mapper;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.constant.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

@Slf4j
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    private final static Integer VERSION_INIT = 1;
    private final static byte LOGIC_DELETE_INIT = 0;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("Start mybatis plus insert fill ....");
        AuthDataHolder.AuthData authData = AuthDataHolder.get();
        this.strictInsertFill(metaObject, "createdAuditData", String.class, "CREATED:TODO");
        this.strictInsertFill(metaObject, "updatedAuditData", String.class, BaseConstant.EmptyString);
        this.strictInsertFill(metaObject, "version", Integer.class, VERSION_INIT);
        this.strictInsertFill(metaObject, "logicDeleted", Byte.class, LOGIC_DELETE_INIT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("Start mybatis plus update fill ....");
        AuthDataHolder.AuthData authData = AuthDataHolder.get();
        this.strictInsertFill(metaObject, "updatedAuditData", String.class, "UPDATED:TODO");
    }
}
