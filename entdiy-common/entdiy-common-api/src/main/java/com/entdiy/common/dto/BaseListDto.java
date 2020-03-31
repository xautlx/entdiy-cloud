package com.entdiy.common.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public abstract class BaseListDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Integer version;
}
