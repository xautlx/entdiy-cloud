package com.entdiy.gen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entdiy.gen.domain.GenTableColumn;

import java.util.List;

/**
 * 业务字段 数据层
 *
 * 
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {
    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);

}
