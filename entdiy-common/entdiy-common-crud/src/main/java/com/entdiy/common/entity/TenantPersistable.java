package com.entdiy.common.entity;

public interface TenantPersistable<ID> {

    String TENANT_ID_COLUMN_NAME="tenant_id";

    ID getTenantId();
}
