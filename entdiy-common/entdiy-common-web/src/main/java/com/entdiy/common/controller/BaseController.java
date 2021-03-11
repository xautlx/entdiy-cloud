package com.entdiy.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.entdiy.common.entity.BaseIdVersionEntity;
import com.entdiy.common.exception.Validation;
import com.entdiy.common.exception.ValidationException;
import com.entdiy.common.model.BatchResult;
import com.entdiy.common.model.PageResult;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.util.BeanUtil;
import com.entdiy.common.web.WebExceptionResolver;
import com.entdiy.common.web.databind.resolver.PageableMethodArgumentResolver;
import com.entdiy.common.web.databind.resolver.QueryWrapperMethodAArgumentResolver;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @param <M>    Service层业务泛型接口
 * @param <T>    Entity泛型对象
 */
@Slf4j
public abstract class BaseController<
        M extends BaseService<T>,
        T extends BaseIdVersionEntity> {

    @Getter
    protected Class<?> entityClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);

    public abstract M getBaseService();

    /**
     * 数据访问控制。比如基于业务对象属性和当前登录对象属性进行判断检测
     *
     * @param entity
     */
    protected void dataAccessControl(T entity) {
        //Do nothing
    }

    @SneakyThrows
    protected <DTO> List<DTO> convertEntitiesToDtoList(List<T> entities, Class<DTO> dtoClass) {
        List<DTO> dtoList = Lists.newArrayList();
        for (T entity : entities) {
            DTO dto = dtoClass.getDeclaredConstructor().newInstance();
            dtoList.add(BeanUtil.copyProperties(entity, dto));
        }
        return dtoList;
    }

    @SneakyThrows
    @ApiOperation(value = "查看")
    protected T entityEditShow(Serializable id) {
        return entityEditShow(id, null);
    }

    @SneakyThrows
    @ApiOperation(value = "查看")
    protected T entityEditShow(Serializable id, Consumer<T> newConsumer) {
        T editDto = (T) entityClass.getDeclaredConstructor().newInstance();
        //新增数据场景，直接返回New DTO
        if (id == null) {
            /**
             * 新构造编辑对象，如果业务UI界面需要对一些新增属性初始化值，可以在此新创建对象基础上设置相关属性值
             * 如：一般有些新增数据时希望初始化一个"编码"属性，可以借助Redis帮助类在此初始化相关编码流水号
             */
            if (newConsumer != null) {
                newConsumer.accept(editDto);
            }
            return editDto;
        }
        T entity = getBaseService().getById(id);
        if (entity == null || entity.isNew()) {
            throw new ValidationException("未查询到数据");
        } else {
            //数据访问控制调用
            dataAccessControl(entity);

            return entity;
        }
    }

    /**
     * POST提交保存数据处理
     *
     * @see #entityEditSave(BaseIdVersionEntity, BiConsumer)
     */
    @SneakyThrows
    @ApiOperation(value = "保存", notes = "返回结果为保存后最新数据")
    public T entityEditSave(@ApiParam("保存数据对象") @RequestBody @Validated T editDto) {
        return this.entityEditSave(editDto, null);
    }

    /**
     * POST提交保存数据处理
     *
     * @param editDto    绑定表单数据的编辑DTO对象
     * @param biConsumer 基于编辑DTO对象和查询对应Entity对象进行额外数据处理
     * @return 基于更新后Entity对象最新属性值构造DTO对象，注意：为了减少网络传输数据量，减少不必要的属性数据返回，只返回主键及最新版本等必要属性支持前端无刷新界面更新隐藏属性
     */
    @SneakyThrows
    @ApiOperation(value = "保存", notes = "返回结果为保存后最新数据")
    public T entityEditSave(@ApiParam("保存数据对象") @RequestBody @Validated T editDto, BiConsumer<T, T> biConsumer) {
        T entity;
        if (editDto.isNotNew()) {
            //数据访问控制调用
            entity = getBaseService().getById(editDto.getId());
            Validation.notNull(entity, "未查询到数据: " + editDto.getId());
            dataAccessControl(entity);
            //乐观锁版本检测
            Validation.isTrue(editDto.getVersion() == null
                            || entity.getVersion() == null
                            || editDto.getVersion().equals(entity.getVersion()),
                    "数据已被他人修改，请尝试刷新界面重新加载数据并修改提交");
        } else {
            entity = (T) entityClass.getDeclaredConstructor().newInstance();
        }

        if (biConsumer != null) {
            biConsumer.accept(editDto, entity);
        }
        getBaseService().saveOrUpdate(editDto, entity);
        return entity;
    }

    /**
     * 单表数据分页查询，将Entity对象集合转换ListDto对象集合。
     * 设计考虑：
     * 之所以没有直接使用Entity对象而是定制的ListDto对象，
     * 1，可以支持细粒度的序列化属性控制，避免不必要的实体属性直接序列化输出暴露
     * 2，为了Swagger文档能输出符合最小化业务属性，避免输出太多无关信息到API文档，干扰前端开发
     *
     * @return
     * @see PageableMethodArgumentResolver
     * @see QueryWrapperMethodAArgumentResolver
     */
    @ApiOperation(value = " 分页查询")
    public PageResult<T> entityQuery(Pageable pageable, QueryWrapper<T> queryWrapper) {
        return getBaseService().queryDtoPage(pageable, queryWrapper);
    }

    public BatchResult<Serializable> entityDelete(Serializable[] ids) {
        return entityDelete(ids, null);
    }

    @ApiOperation(value = "删除")
    public BatchResult<Serializable> entityDelete(Serializable[] ids, Predicate<T> deletePredicate) {
        BatchResult batchResult = new BatchResult();
        if (ids != null) {
            //对于批量删除,循环每个对象调用Service接口删除,则各对象删除事务分离
            //这样可以方便某些对象删除失败不影响其他对象删除
            //如果业务逻辑需要确保批量对象删除在同一个事务则请子类覆写调用Service的批量删除接口
            for (Serializable id : ids) {
                T entity = getBaseService().getById(id);
                if (entity == null) {
                    batchResult.appendFailureItem(id, "数据不存在");
                    continue;
                }
                //数据访问控制调用
                dataAccessControl(entity);

                if (deletePredicate != null && !deletePredicate.test(entity)) {
                    batchResult.appendFailureItem(entity.getId(), "删除操作取消");
                    continue;
                }

                try {
                    if (getBaseService().removeByIdWithFill(entity)) {
                        batchResult.appendSuccessItem(entity.getId());
                    } else {
                        batchResult.appendFailureItem(entity.getId(), "删除操作失败");
                    }
                } catch (Exception e) {
                    String errorMessage = null;
                    if (e instanceof DataIntegrityViolationException) {
                        Throwable cause = e;
                        while (cause != null) {
                            String sqlMessage = cause.getMessage();
                            if (sqlMessage.indexOf("FK") > -1) {
                                //提取外键名称，方便用户反馈问题是排查告知具体关联数据逻辑问题
                                //for MySQL
                                errorMessage = "该数据已被关联使用：FK" + StringUtils.substringBetween(sqlMessage, "FK", "`");
                                break;
                                //外键约束异常，不做logger日志
                            }
                            cause = cause.getCause();
                        }
                    }
                    if (errorMessage == null) {
                        //构建和记录友好和详细的错误信息及消息
                        //生成一个异常流水号，追加到错误消息上显示到前端用户，用户反馈问题时给出此流水号给运维或开发人员快速定位对应具体异常细节
                        String exceptionCode = WebExceptionResolver.buildEID();
                        errorMessage = exceptionCode + ": 数据删除操作失败，请联系管理员处理！";
                        log.error(errorMessage, e);
                    }
                    batchResult.appendFailureItem(entity.getId(), errorMessage);
                }
            }
        }
        return batchResult;
    }
}
