package com.entdiy.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.strategy.SuperClassStrategy;
import com.entdiy.common.dto.BaseEditDto;
import com.entdiy.common.dto.BaseListDto;
import com.entdiy.common.dto.BaseTreeEditDto;
import com.entdiy.common.dto.BaseTreeListDto;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.entity.BaseTreeEntity;
import com.entdiy.common.entity.TreePersistable;

import java.util.List;

public class FieldParseSuperClassStrategy implements SuperClassStrategy {

    private boolean isTreeEntity(TableInfo tableInfo) {
        List<TableField> commonFields = tableInfo.getCommonFields();
        for (TableField tableField : commonFields) {
            if (TreePersistable.PARENT_ID_COLUMN_NAME.equals(tableField.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Class<?>[] candidateSuperEntityClass() {
        return new Class[]{BaseTreeEntity.class, BaseEntity.class};
    }

    @Override
    public Class<? extends BaseEntity> parseSuperEntityClass(TableInfo tableInfo) {
        return isTreeEntity(tableInfo) ? BaseTreeEntity.class : BaseEntity.class;
    }

    @Override
    public Class<? extends BaseListDto> parseSuperListDtoClass(TableInfo tableInfo) {
        return isTreeEntity(tableInfo) ? BaseTreeListDto.class : BaseListDto.class;
    }

    @Override
    public Class<? extends BaseEditDto> parseSuperEditDtoClass(TableInfo tableInfo) {
        return isTreeEntity(tableInfo) ? BaseTreeEditDto.class : BaseEditDto.class;
    }

}
