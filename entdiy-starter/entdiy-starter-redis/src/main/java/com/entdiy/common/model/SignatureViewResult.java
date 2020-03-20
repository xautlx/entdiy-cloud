package com.entdiy.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignatureViewResult<T> extends ViewResult<T> {

    private String signature;

    private String nonce;

    /**
     * 此属性约定放在最后面且始终有值，以方便于字符串形式提取JSON结构数据项
     */
    private Long timestamp;
}

