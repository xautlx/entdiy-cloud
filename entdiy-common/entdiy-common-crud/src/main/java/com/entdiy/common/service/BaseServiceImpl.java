package com.entdiy.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.entdiy.common.entity.BaseIdVersionEntity;
import com.entdiy.common.entity.BaseTreeEntity;
import com.entdiy.common.exception.Validation;
import com.entdiy.common.mapper.BaseMyBatisMapper;
import com.entdiy.common.model.PageResult;
import com.entdiy.common.util.BeanUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 注意：MyBatis Plus框架默认是采用的策略是只对读写类方法级别添加@Transactional注解，读取类方法未添加事务注解。
 * 因此，对于业务定制新增的方法一定要注意根据数据操作读写类型，为其添加读写事务注解，否则会出现事务控制问题！！！
 *
 * @param <M>    mapper泛型类
 * @param <T>    entity泛型类
 * @see com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
 */
public abstract class BaseServiceImpl<
        M extends BaseMyBatisMapper<T>,
        T extends BaseIdVersionEntity> implements BaseService<T> {

    @Autowired
    protected M baseMapper;

    @Getter
    protected Class<?> entityClass;

    @Getter
    protected String entityClassName;

    protected String tableName;

    @PostConstruct
    public void init() {
        entityClass = ReflectionKit.getSuperClassGenericType(getClass(), 1);
        entityClassName = entityClass.getName();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        tableName = tableInfo.getTableName();
    }

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = BaseService.ENTITY_CACHE_NAME, key = "#p0", unless = "#result == null")
    public T getById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public T getOne(Wrapper<T> queryWrapper) {
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> list() {
        return list(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> list(Wrapper<T> queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = Wrappers.emptyWrapper();
        }
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = "#p0.id", condition = "#p0!=null && #p0.id!=null"),
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = BaseService.ENTITY_CACHE_KEY_ALL)
    })
    public boolean saveOrUpdate(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
            return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ?
                    SqlHelper.retBool(getBaseMapper().insert(entity)) : SqlHelper.retBool(getBaseMapper().updateById(entity));
        }
        return false;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = "#p0.id", condition = "#p0!=null && #p0.id!=null"),
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = BaseService.ENTITY_CACHE_KEY_ALL)
    })
    public boolean removeByIdWithFill(T entity) {
        return SqlHelper.retBool(((BaseMyBatisMapper) getBaseMapper()).deleteByIdWithFill(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, allEntries = true)
    public int removeByIdsWithFill(T... entities) {
        int count = 0;
        for (T entity : entities) {
            if (removeByIdWithFill(entity)) {
                count++;
            }
        }
        return count;
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public PageResult<T> queryDtoPage(Pageable pageable, Wrapper<T> queryWrapper) {
        IPage<T> mybatisPlusPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        mybatisPlusPage.setSize(pageable.getPageSize());
        //MyBatisPlus从1开始，JPA页码从0开始
        mybatisPlusPage.setCurrent(pageable.getPageNumber() + 1);
        IPage<T> entityPage = getBaseMapper().selectPage(mybatisPlusPage, queryWrapper);
        List<T> entities = entityPage.getRecords();
        return new PageResult(new PageImpl(entities, pageable, entityPage.getTotal()));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = "#p1.id", condition = "#p1!=null && #p1.id!=null"),
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = BaseService.ENTITY_CACHE_KEY_ALL)
    })
    public boolean saveOrUpdate(T editDto, T entity) {
        //树形结构数据公共属性级联处理：
        //1，对编辑提交的parentId非正值则标识置空
        //2，对parentIdPath冗余属性做级联批量更新处理
        if (entity instanceof BaseTreeEntity) {
            BaseTreeEntity treeEntity = (BaseTreeEntity) entity;
            BaseTreeEntity treeEditDto = (BaseTreeEntity) editDto;
            Long oldParentId = treeEntity.getParentId();
            Long newParentId = treeEditDto.getParentId();

            //只有表单数据包含parentId才处理
            if (newParentId != null) {
                if (treeEditDto.isNew()) {//新增
                    if (newParentId > 0) { //有父节点则级联设置节点路径
                        BaseTreeEntity parentTreeEntity = (BaseTreeEntity) getById(newParentId);
                        treeEntity.setParentIdPath(parentTreeEntity.getCurrentIdPath());

                        //如果新挂父节点原先为叶子节点，则更新为非叶子节点
                        if (parentTreeEntity.getLeafNode()) {
                            parentTreeEntity.setLeafNode(Boolean.FALSE);
                            getBaseMapper().updateById((T) parentTreeEntity);
                        }
                    } else { //非正值未挂父节点则相关标识置空
                        treeEditDto.setParentId(null);
                        treeEntity.setParentId(null);
                        treeEntity.setParentIdPath("");
                    }
                } else { //编辑
                    //变更父节点级联处理
                    if (ObjectUtils.notEqual(newParentId, oldParentId)) {
                        String oldChildrenParentIdPathPrefix = treeEntity.getCurrentIdPath();
                        BaseTreeEntity newParentTreeEntity = (BaseTreeEntity) getById(newParentId);
                        if (newParentId > 0) {//原无父节点，变更为有父节点
                            treeEntity.setParentIdPath(newParentTreeEntity.getCurrentIdPath());

                            //如果新挂父节点原先为叶子节点，则更新为非叶子节点
                            if (newParentTreeEntity.getLeafNode()) {
                                newParentTreeEntity.setLeafNode(Boolean.FALSE);
                                getBaseMapper().updateById((T) newParentTreeEntity);
                            }
                        } else {//非正值则标识置空：原有父节点，变更为无父节点
                            treeEditDto.setParentId(null);
                            treeEntity.setParentId(null);
                            treeEntity.setParentIdPath("");

                            //对原先挂父节点查询其是否还有子节点，如果没有了则更新为叶子节点
                            QueryWrapper<T> childrenQuery = Wrappers.query();
                            childrenQuery.eq(BaseTreeEntity.PARENT_ID_COLUMN_NAME, oldParentId);
                            Integer childrenCount = getBaseMapper().selectCount(childrenQuery);
                            if (childrenCount <= 1) {
                                BaseTreeEntity oldParentTreeEntity = (BaseTreeEntity) getById(oldParentId);
                                oldParentTreeEntity.setLeafNode(Boolean.TRUE);
                                getBaseMapper().updateById((T) oldParentTreeEntity);
                            }
                        }
                        String newChildrenParentIdPathPrefix = treeEntity.getCurrentIdPath();

                        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.likeRight(BaseTreeEntity.PARENT_ID_PATH_COLUMN_NAME, oldChildrenParentIdPathPrefix);
                        getBaseMapper().cascadeUpdateChildrenParentIdPath(
                                updateWrapper,
                                tableName,
                                oldChildrenParentIdPathPrefix,
                                newChildrenParentIdPathPrefix);
                    }
                }
            }
        }
        BeanUtil.copyProperties(editDto, entity);
        int count = entity.isNew() ? getBaseMapper().insert(entity) : getBaseMapper().updateById(entity);
        Validation.isTrue(count == 1, "数据未正确写入");
        return true;
    }
}
