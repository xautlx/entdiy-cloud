package com.entdiy.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entdiy.gen.domain.GenTableColumn;
import com.entdiy.gen.mapper.GenTableColumnMapper;
import com.entdiy.gen.service.IGenTableColumnService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 业务字段 服务层实现
 *
 * 
 */
@Service
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        return list(new LambdaQueryWrapper<GenTableColumn>()
                .eq(GenTableColumn::getTableId,tableId)
                .orderByAsc(GenTableColumn::getSort));
    }

    /**
     * 新增业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn) {
        return baseMapper.insert(genTableColumn);
    }

    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn) {
        return baseMapper.updateById(genTableColumn);
    }

    /**
     * 删除业务字段对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGenTableColumnByIds(String ids) {
        return baseMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
    }
}
