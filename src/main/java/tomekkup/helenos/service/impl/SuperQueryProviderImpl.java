package tomekkup.helenos.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import java.io.Serializable;
import tomekkup.helenos.Converter;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.SuperQueryProvider;
import tomekkup.helenos.types.Column;
import tomekkup.helenos.types.Slice;
import tomekkup.helenos.types.qx.query.SingleSubColumnQuery;
import java.util.ArrayList;
import java.util.List;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
    public <K, SN, N, V> List<Slice<K, N, V>> predicate(tomekkup.helenos.types.qx.query.SubRangeQuery<K, SN, N, V> query) {
        K key = Converter.toValue(query.getKeyFrom(), query.getKeyClass());
        SubSliceQuery<K, SN, N, V> cq = HFactory.createSubSliceQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(key);
        cq.setSuperColumn(Converter.toValue(query.getsName(), query.getsNameClass()));
        if (CollectionUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMaxResults());
        } else {
            cq.setColumnNames(Converter.toValue(query.getColumnNames(), query.getNameClass()));
        }

        QueryResult<ColumnSlice<N, V>> qr = cq.execute();

        List<Slice<K, N, V>> ret = new ArrayList<Slice<K, N, V>>(1);
        if (qr != null) {
            ret.add(new Slice(key, toJsonColumns(qr.get().getColumns())));
        }

        return ret;
    }

    @Override
    public <K, SN, N, V> List<Slice<K, N, V>> keyRange(tomekkup.helenos.types.qx.query.SubRangeQuery<K, SN, N,V> query) {
        RangeSubSlicesQuery<K, SN, N, V> cq = HFactory.createRangeSubSlicesQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());

        cq.setKeys(Converter.toValue(query.getKeyFrom(), query.getKeyClass()), Converter.toValue(query.getKeyTo(), query.getKeyClass()));
        cq.setSuperColumn(Converter.toValue(query.getsName(), query.getsNameClass()));
        if (CollectionUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMaxResults());
        } else {
            cq.setColumnNames(Converter.toValue(query.getColumnNames(), query.getNameClass()));
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
