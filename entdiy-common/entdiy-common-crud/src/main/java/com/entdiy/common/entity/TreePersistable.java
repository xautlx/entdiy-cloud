package com.entdiy.common.entity;

public interface TreePersistable<ID> {

    String PARENT_ID_COLUMN_NAME="parent_id";

    ID getParentId();
}
