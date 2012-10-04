package com.kuprowski.helenos.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.kuprowski.helenos.Converter;
import com.kuprowski.helenos.service.ClusterConfigAware;
import com.kuprowski.helenos.service.StandardQueryProvider;
import com.kuprowski.helenos.types.Column;
import com.kuprowski.helenos.types.Slice;
import com.kuprowski.helenos.types.qx.query.SingleColumnQuery;
import java.util.ArrayList;
import java.util.List;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;
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
@Component("standardQueryProvider")
public class StandardQueryProviderImpl extends AbstractQueryProvider implements StandardQueryProvider, ClusterConfigAware {

    @Override
    public <K, N> Column<N> singleColumn(@JsonRpcParam("query") SingleColumnQuery<K, N> query) {
        ColumnQuery<K, N, String> cq = HFactory.createColumnQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(Converter.toValue(query.getKey(), query.getKeyClass()));
        cq.setName(Converter.toValue(query.getName(), query.getNameClass()));

        HColumn<N, String> column = cq.execute().get();
        return mapper.map(column, Column.class);
    }
    
    @Override
    public <K, N> List<Slice<K,N>> predicate(com.kuprowski.helenos.types.qx.query.RangeQuery<K, N> query) {
        K key = Converter.toValue(query.getKeyFrom(), query.getKeyClass());
        SliceQuery<K, N, String> cq = HFactory.createSliceQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(key);
        if (CollectionUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMaxResults());
        } else {
            cq.setColumnNames(Converter.toValue(query.getColumnNames(),query.getNameClass()));
        }
        
        QueryResult<ColumnSlice<N, String>> qr = cq.execute();
        
        List<Slice<K,N>> ret = new ArrayList<Slice<K,N>>(1);
        if (qr != null) {
            ret.add(new Slice(key, toJsonColumns(qr.get().getColumns())));
        }
        
        return ret;
    }
    
    @Override
    public <K, N> List<Slice<K,N>> keyRange(com.kuprowski.helenos.types.qx.query.RangeQuery<K, N> query) {
        RangeSlicesQuery<K, N, String> cq = HFactory.createRangeSlicesQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKeys(Converter.toValue(query.getKeyFrom(), query.getKeyClass()), Converter.toValue(query.getKeyTo(), query.getKeyClass()));
        
        if (CollectionUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMaxResults());
        } else {
            cq.setColumnNames(Converter.toValue(query.getColumnNames(),query.getNameClass()));
        }
        
        QueryResult<OrderedRows<K, N, String>> qr = cq.execute();
        
        List<Slice<K,N>> ret = new ArrayList<Slice<K,N>>(1);
        if (qr != null) {
            List<Row<K, N, String>> rows = qr.get().getList();
            for (Row<K, N, String> row : rows) {
                ColumnSlice<N, String> colSlice = row.getColumnSlice();
                ret.add(new Slice(row.getKey(), toJsonColumns(colSlice.getColumns())));
            }
        }
        
        return ret;
    }
}
