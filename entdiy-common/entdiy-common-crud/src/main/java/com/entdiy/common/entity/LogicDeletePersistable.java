package com.entdiy.common.entity;

public interface LogicDeletePersistable {

    String LOGIC_DELETED_COLUMN_NAME = "logic_deleted";

    Byte getLogicDeleted();
}
