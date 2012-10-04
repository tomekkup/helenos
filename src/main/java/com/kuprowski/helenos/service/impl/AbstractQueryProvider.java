package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.Converter;
import com.kuprowski.helenos.service.QueryProvider;
import com.kuprowski.helenos.types.Column;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public abstract class AbstractQueryProvider extends AbstractProvider implements QueryProvider {

    protected <N> List<Column<N>> toJsonColumns(List<HColumn<N, String>> columns) {
        List<Column<N>> retColumns = new ArrayList<Column<N>>(1);
        Iterator<HColumn<N,String>> iterator = columns.iterator();
        while(iterator.hasNext()) {
            retColumns.add(mapper.map(iterator.next(), Column.class));
        }
        return retColumns;
    }
    
    /*
    @Override
    public <K, N> List<K> keyRange(com.kuprowski.helenos.types.qx.query.KeyRangeQuery<K, N> query) {
        RangeSlicesQuery<K, N, String> cq = HFactory.createRangeSlicesQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKeys(Converter.toValue(query.getKeyFrom(), query.getKeyClass()), Converter.toValue(query.getKeyTo(), query.getKeyClass()));
        //cq.setReturnKeysOnly();
        cq.setRowCount(query.getMaxResults());

        QueryResult<OrderedRows<K, N, String>> qr = cq.execute();

        List<K> ret = new ArrayList<K>();
        if (qr != null) {
            List<Row<K, N, String>> items = qr.get().getList();
            for (Row<K, N, String> item : items) {
                ret.add(item.getKey());
            }
        }

        return ret;
    }*/
}
