package com.entdiy.common.dto;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseListDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Integer version;
}
