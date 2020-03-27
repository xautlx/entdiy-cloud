package com.entdiy.common.entity;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ActiveRecordModel {

    /**
     * 插入（字段选择插入）
     */
    public boolean save() {
        SqlSession sqlSession = sqlSession();
        try {
            return SqlHelper.retBool(sqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), this));
        } finally {
            closeSqlSession(sqlSession);
        }
    }

    /**
     * 更新（字段选择更新）
     */
    public boolean update() {
        Assert.isFalse(StringUtils.checkValNull(pkVal()), "updateById primaryKey is null.");
        // updateById
        Map<String, Object> map = new HashMap<>(1);
        map.put(Constants.ENTITY, this);
        SqlSession sqlSession = sqlSession();
        try {
            return SqlHelper.retBool(sqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), map));
        } finally {
            closeSqlSession(sqlSession);
        }
    }

    /**
     * 插入 OR 更新
     */
    public boolean saveOrUpdate() {
        Serializable pk = pkVal();
        if (pk == null || StringUtils.isBlank(pk.toString())) {
            return save();
        } else {
            return update();
        }
    }

    /**
     * 根据主键删除
     */
    public boolean delete() {
        Serializable pk = pkVal();
        Assert.isFalse(StringUtils.checkValNull(pk), "deleteById primaryKey is null.");
        SqlSession sqlSession = sqlSession();
        try {
            return SqlHelper.retBool(sqlSession.delete(sqlStatement(SqlMethod.DELETE_BY_ID), pk));
        } finally {
            closeSqlSession(sqlSession);
        }
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod sqlMethod
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return sqlStatement(sqlMethod.getMethod());
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod sqlMethod
     */
    protected String sqlStatement(String sqlMethod) {
        return SqlHelper.table(getClass()).getSqlStatement(sqlMethod);
    }

    /**
     * 主键值
     */
    protected Serializable pkVal() {
        return (Serializable) ReflectionKit.getMethodValue(this, TableInfoHelper.getTableInfo(getClass()).getKeyProperty());
    }


    /**
     * 获取Session 默认自动提交
     */
    protected SqlSession sqlSession() {
        return SqlHelper.sqlSession(getClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(getClass()));
    }
}
