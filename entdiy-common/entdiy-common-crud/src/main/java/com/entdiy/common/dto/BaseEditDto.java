package com.entdiy.common.dto;

import com.baomidou.mybatisplus.extension.api.R;
import com.entdiy.common.entity.BaseIdVersionEntity;
import com.google.common.collect.Lists;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.function.Function;

public class BaseEditDto extends BaseIdVersionEntity {

    private List<Function<T, R>> newFunctions = null;
    private List<Function<T, R>> updateFunctions = null;

    public BaseEditDto forNew(Function<T, R> func) {
        if (newFunctions == null) {
            newFunctions = Lists.newArrayList();
        }
        newFunctions.add(func);
        return this;
    }

    public BaseEditDto forUpdate(Function<T, R> func) {
        if (updateFunctions == null) {
            updateFunctions = Lists.newArrayList();
        }
        updateFunctions.add(func);
        return this;
    }
}
