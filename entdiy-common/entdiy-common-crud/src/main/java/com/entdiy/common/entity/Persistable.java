package com.entdiy.common.entity;

import org.springframework.lang.Nullable;

public interface Persistable<ID> {

    /**
     * Returns the id of the entity.
     *
     * @return the id. Can be {@literal null}.
     */
    @Nullable
    ID getId();

    /**
     * Returns if the {@code Persistable} is new or was persisted already.
     *
     * @return if {@literal true} the object is new.
     */
    boolean isNew();
}
