package com.entdiy.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseEditDto {

    @Getter
    @Setter
    @ApiModelProperty(value = "主键")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty(value = "乐观锁版本")
    private Integer version;

//    private List<Function<T, R>> newFunctions = null;
//    private List<Function<T, R>> updateFunctions = null;
//
//    public BaseEditDto forNew(Function<T, R> func) {
//        if (newFunctions == null) {
//            newFunctions = Lists.newArrayList();
//        }
//        newFunctions.add(func);
//        return this;
//    }
//
//    public BaseEditDto forUpdate(Function<T, R> func) {
//        if (updateFunctions == null) {
//            updateFunctions = Lists.newArrayList();
//        }
//        updateFunctions.add(func);
//        return this;
//    }
}
