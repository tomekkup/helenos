package tomekkup.helenos.service.query.impl;

import java.util.ArrayList;
import java.util.List;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;
import me.prettyprint.hector.api.query.SubSliceQuery;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.impl.AbstractQueryProvider;
import tomekkup.helenos.service.query.SuperQueryProvider;
import tomekkup.helenos.types.Slice;
import tomekkup.helenos.types.qx.query.QxSubPredicateQuery;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Component("superQueryProvider")
public class SuperQueryProviderImpl extends AbstractQueryProvider implements SuperQueryProvider, ClusterConfigAware {

    @Override
    public <K, SN, N, V> List<Slice<K, N, V>> predicate(QxSubPredicateQuery<K, SN, N, V> query) {
        logQueryObject(query);
        SubSliceQuery<K, SN, N, V> cq = HFactory.createSubSliceQuery(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(query.getKey());
        cq.setSuperColumn(query.getsName());
        
        if (ArrayUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(query.getNameStart(), query.getNameEnd(), query.isReversed(), query.getLimit());
        } else {
            cq.setColumnNames(query.getColumnNames());
        }

        QueryResult<ColumnSlice<N, V>> qr = cq.execute();

        List<Slice<K, N, V>> ret = new ArrayList<Slice<K, N, V>>(1);
        if (qr != null) {
            ret.add(new Slice(query.getKey(), toJsonColumns(qr.get().getColumns())));
        }

        return ret;
    }

    @Override
    public <K, SN, N, V> List<Slice<K, N, V>> keyRange(tomekkup.helenos.types.qx.query.QxSubRangeQuery<K, SN, N,V> query) {
        logQueryObject(query);
        RangeSubSlicesQuery<K, SN, N, V> cq = HFactory.createRangeSubSlicesQuery(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());

        cq.setKeys(query.getKeyFrom(), query.getKeyTo());
        cq.setSuperColumn(query.getsName());
        
        if (ArrayUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(query.getNameStart(), query.getNameEnd(), query.isReversed(), query.getLimit());
        } else {
            cq.setColumnNames(query.getColumnNames());
        }

        QueryResult<OrderedRows<K, N, V>> qr = cq.execute();

        List<Slice<K, N, V>> ret = new ArrayList<Slice<K, N, V>>(1);
        if (qr != null) {
            List<Row<K, N, V>> rows = qr.get().getList();
            for (Row<K, N, V> row : rows) {
                ColumnSlice<N, V> colSlice = row.getColumnSlice();
                ret.add(new Slice(row.getKey(), toJsonColumns(colSlice.getColumns())));
            }
        }

        return ret;
    }
}
