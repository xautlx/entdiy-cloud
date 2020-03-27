package com.entdiy.common.test.dialect;

import com.entdiy.common.entity.LogicDeletePersistable;
import com.entdiy.common.entity.TenantPersistable;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.unique.DefaultUniqueDelegate;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.UniqueKey;

import java.util.Iterator;

public class TenantH2UniqueDelegate extends DefaultUniqueDelegate {

    public TenantH2UniqueDelegate(Dialect dialect) {
        super(dialect);
    }

    @Override
    protected String uniqueConstraintSql(UniqueKey uniqueKey) {
        final StringBuilder sb = new StringBuilder();
        sb.append( "unique (" );
        final Iterator<org.hibernate.mapping.Column> columnIterator = uniqueKey.columnIterator();
        while ( columnIterator.hasNext() ) {
            final org.hibernate.mapping.Column column = columnIterator.next();
            sb.append( column.getQuotedName( dialect ) );
            if ( uniqueKey.getColumnOrderMap().containsKey( column ) ) {
                sb.append( " " ).append( uniqueKey.getColumnOrderMap().get( column ) );
            }
            if ( columnIterator.hasNext() ) {
                sb.append( ", " );
            }
        }

        //多租户、逻辑删除约束字段追加
        Iterator tableColumns = uniqueKey.getTable().getColumnIterator();
        while (tableColumns.hasNext()) {
            final Column column = (Column) tableColumns.next();
            if (TenantPersistable.TENANT_ID_COLUMN_NAME.equals(column.getName())
                    || LogicDeletePersistable.LOGIC_DELETED_COLUMN_NAME.equals(column.getName())) {
                sb.append(", ").append(column.getName());
            }
        }

        return sb.append( ')' ).toString();
    }
}
