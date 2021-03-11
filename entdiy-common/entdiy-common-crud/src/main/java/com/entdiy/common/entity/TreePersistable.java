package com.entdiy.common.entity;

public interface TreePersistable<ID> {

    String PARENT_ID_COLUMN_NAME="parent_id";

    String PARENT_ID_PATH_COLUMN_NAME="parent_id_path";

    String LEAF_NODE_COLUMN_NAME="leaf_node";

    ID getParentId();

    String getParentIdPath();
}
