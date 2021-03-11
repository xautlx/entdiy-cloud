package com.entdiy.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.entdiy.common.entity.BaseIdVersionEntity;
import com.entdiy.common.mapper.BaseMyBatisMapper;
import com.entdiy.common.model.PageResult;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * 之所以没有直接继承MyBatis Plus的IService接口，主要为了对业务接口做更精准的控制，只开放框架实际用到接口方法。
 *
 * @param <T>
 * @see com.baomidou.mybatisplus.extension.service.IService
 */
public interface BaseService<T extends BaseIdVersionEntity> {

    String ENTITY_CACHE_NAME = "ENTITY";
    String ENTITY_CACHE_KEY_ALL = "'ALL'";

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMyBatisMapper<T> getBaseMapper();

    /**
     * 根据 ID 查询, 有多个 result 是否抛出异常
     *
     * @param id 主键ID
     */
    T getById(Serializable id);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    T getOne(Wrapper<T> queryWrapper);

    /**
     * 查询全部数据列表。请勿随意使用，尤其是对于大表数据访问，一次性过多数据查询返回会导致内存溢出问题。
     */
    List<T> list();

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    List<T> list(Wrapper<T> queryWrapper);

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean saveOrUpdate(T entity);

    /**
     * 基于前端表单组装的编辑DTO对象创建或更新实体对象
     *
     * @param edto   编辑DTO对象
     * @param entity 实体对象
     * @return
     */
    boolean saveOrUpdate(T edto, T entity);

    /**
     * 支持自动填充属性处理的根据 entity 逻辑批量删除
     * 特别注意：只有调用此方法传入entity对象进行删除才会做自动填充属性处理，其余按照id等删除接口不会自动做属性填充处理
     *
     * @param entity 实体对象
     * @return 成功删除操作
     */
    boolean removeByIdWithFill(T entity);

    /**
     * 支持自动填充属性处理的根据 entity 逻辑批量删除
     * 特别注意：只有调用此方法传入entity对象进行删除才会做自动填充属性处理，其余按照id等删除接口不会自动做属性填充处理
     *
     * @param entities 实体对象
     * @return 成功删除操作的记录数
     */
    int removeByIdsWithFill(T... entities);

    /**
     * 基于Entity查询结果转换为ListDto翻页查询
     *
     * @param pageable     翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    PageResult<T> queryDtoPage(Pageable pageable, Wrapper<T> queryWrapper);
}
