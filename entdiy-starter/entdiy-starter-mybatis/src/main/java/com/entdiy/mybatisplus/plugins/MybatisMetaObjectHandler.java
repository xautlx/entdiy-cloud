package com.entdiy.mybatisplus.plugins;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.entdiy.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 特别注意：只有基于entity参数相关的增删改Mapper接口才会自动进行填充输出处理，其余基于id或map参数等接口不会自动记录填充属性！！！
 */
@Slf4j
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    private final static Integer VERSION_INIT = 1;
    private final static byte LOGIC_DELETE_INIT = 0;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("Start mybatis plus insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUserId() + ":" + SecurityUtils.getUsername());
        this.strictInsertFill(metaObject, "version", Integer.class, VERSION_INIT);
        this.strictInsertFill(metaObject, "logicDel", Byte.class, LOGIC_DELETE_INIT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("Start mybatis plus update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getUserId() + ":" + SecurityUtils.getUsername());
    }

    /**
     * 采用宽松通用属性值设置模式，始终强制覆盖
     *
     * @param metaObject
     * @param fieldName
     * @param fieldVal
     * @return
     */
    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<Object> fieldVal) {
        Object obj = fieldVal.get();
        if (Objects.nonNull(obj)) {
            metaObject.setValue(fieldName, obj);
        }
        return this;
    }
}
