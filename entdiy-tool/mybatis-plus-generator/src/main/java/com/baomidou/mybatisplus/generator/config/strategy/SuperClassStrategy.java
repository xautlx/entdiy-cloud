package com.baomidou.mybatisplus.generator.config.strategy;


import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 * 动态的基类策略接口定义
 */
public interface SuperClassStrategy {

    /**
     * 返回候选用到的实体基类列表，用于合并计算superEntityColumns
     *
     * @return
     */
    Class<?>[] candidateSuperEntityClass();

    /**
     * 基于表结构对象返回对应的Entity基类，如按照表中包含特定字段返回不同继承基类
     *
     * @param tableInfo
     * @return
     */
    Class<?> parseSuperEntityClass(TableInfo tableInfo);

    /**
     * 基于表结构对象返回对应的ListDto基类，如按照表中包含特定字段返回不同继承基类
     *
     * @param tableInfo
     * @return
     */
    Class<?> parseSuperListDtoClass(TableInfo tableInfo);

    /**
     * 基于表结构对象返回对应的EditDto基类，如按照表中包含特定字段返回不同继承基类
     *
     * @param tableInfo
     * @return
     */
    Class<?> parseSuperEditDtoClass(TableInfo tableInfo);

}
