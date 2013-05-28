package tomekkup.helenos.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import tomekkup.helenos.Converter;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.StandardQueryProvider;
import tomekkup.helenos.types.Slice;
import java.util.ArrayList;
import java.util.List;
import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
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
    public <K, N, V> List<Slice<K,N,V>> cql(@JsonRpcParam("query") tomekkup.helenos.types.qx.query.CqlQuery<K,N,V> query) {
        CqlQuery<K,N,V> cqlQuery = new CqlQuery<K, N, V>(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cqlQuery.setQuery(query.getQuery());
        QueryResult<CqlRows<K, N, V>> qr = cqlQuery.execute();
        
        List<Slice<K,N,V>> ret = new ArrayList<Slice<K,N,V>>(1);
        if (qr != null) {
            List<Row<K, N, V>> rows = qr.get().getList();
            for (Row<K, N, V> row : rows) {
                ColumnSlice<N, V> colSlice = row.getColumnSlice();
                ret.add(new Slice(row.getKey(), toJsonColumns(colSlice.getColumns())));
            }
        }
        return ret;
    }
    
    @Override
    public <K, N, V> List<Slice<K,N,V>> predicate(tomekkup.helenos.types.qx.query.RangeQuery<K,N,V> query) {
        K key = Converter.toValue(query.getKeyFrom(), query.getKeyClass());
        SliceQuery<K, N, V> cq = HFactory.createSliceQuery(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(key);
        if (CollectionUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMaxResults());
        } else {
            cq.setColumnNames(Converter.toValue(query.getColumnNames(),query.getNameClass()));
        }
        
        QueryResult<ColumnSlice<N, V>> qr = cq.execute();
        
        List<Slice<K,N,V>> ret = new ArrayList<Slice<K,N,V>>(1);
        if (qr != null) {
            ret.add(new Slice(key, toJsonColumns(qr.get().getColumns())));
        }
        
        return ret;
    }
    
    @Override
    public <K, N, V> List<Slice<K,N,V>> keyRange(tomekkup.helenos.types.qx.query.RangeQuery<K,N,V> query) {
        RangeSlicesQuery<K, N, V> cq = HFactory.createRangeSlicesQuery(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKeys(Converter.toValue(query.getKeyFrom(), query.getKeyClass()), Converter.toValue(query.getKeyTo(), query.getKeyClass()));
        
        if (CollectionUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMaxResults());
        } else {
            cq.setColumnNames(Converter.toValue(query.getColumnNames(),query.getNameClass()));
        }
        
        QueryResult<OrderedRows<K, N, V>> qr = cq.execute();
        
        List<Slice<K,N,V>> ret = new ArrayList<Slice<K,N,V>>(1);
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
