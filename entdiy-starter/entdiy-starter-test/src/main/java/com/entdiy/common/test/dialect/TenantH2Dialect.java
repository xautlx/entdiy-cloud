package com.entdiy.common.test.dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.unique.UniqueDelegate;

public class TenantH2Dialect extends H2Dialect {
    private UniqueDelegate uniqueDelegate = new TenantH2UniqueDelegate(this);

    @Override
    public UniqueDelegate getUniqueDelegate() {
        return uniqueDelegate;
    }
}
