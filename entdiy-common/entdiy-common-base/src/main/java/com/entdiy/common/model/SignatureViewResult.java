package com.entdiy.common.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignatureViewResult<T> extends ViewResult<T> {

    private String signature;

    private String nonce;

}

