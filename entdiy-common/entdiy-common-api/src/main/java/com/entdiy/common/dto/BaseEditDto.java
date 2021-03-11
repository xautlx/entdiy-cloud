package com.entdiy.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public boolean isNew() {
        Long id = getId();
        if (id == null || id <= 0) {
            return true;
        }
        return false;
    }

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public boolean isEditing() {
        Long id = getId();
        if (id != null && id > 0) {
            return true;
        }
        return false;
    }

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
