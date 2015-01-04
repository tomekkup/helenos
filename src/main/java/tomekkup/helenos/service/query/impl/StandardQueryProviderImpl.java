package tomekkup.helenos.service.query.impl;

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
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.impl.AbstractQueryProvider;
import tomekkup.helenos.service.query.StandardQueryProvider;
import tomekkup.helenos.types.Slice;
import tomekkup.helenos.types.qx.query.QxCqlQuery;
import tomekkup.helenos.types.qx.query.QxPredicateQuery;
import tomekkup.helenos.types.qx.query.QxRangeQuery;

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

    public StandardQueryProviderImpl() {
        super();
        super.logger = Logger.getLogger(this.getClass());
    }
    
    @Override
    public <K, N, V> List<Slice<K,N,V>> cql(QxCqlQuery<K,N,V> query) {
        logQueryObject(query);
        CqlQuery<K,N,V> cqlQuery = new CqlQuery<K, N, V>(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cqlQuery.setQuery(query.getQuery());
        QueryResult<CqlRows<K, N, V>> qr = cqlQuery.execute();
        
        List<Slice<K,N,V>> ret = new ArrayList<Slice<K,N,V>>(1);
        if (qr != null && qr.get() != null) {
            List<Row<K, N, V>> rows = qr.get().getList();
            for (Row<K, N, V> row : rows) {
                ColumnSlice<N, V> colSlice = row.getColumnSlice();
                ret.add(new Slice(row.getKey(), toJsonColumns(colSlice.getColumns())));
            }
        }
        return ret;
    }
    
    @Override
    public <K, N, V> List<Slice<K,N,V>> predicate(QxPredicateQuery<K,N,V> query) {
        logQueryObject(query);
        SliceQuery<K, N, V> cq = HFactory.createSliceQuery(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(query.getKey());
        
        if (ArrayUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(query.getNameStart(), query.getNameEnd(), query.isReversed(), query.getLimit());
        } else {
            cq.setColumnNames(query.getColumnNames());
        }
        
        QueryResult<ColumnSlice<N, V>> qr = cq.execute();
        
        List<Slice<K,N,V>> ret = new ArrayList<Slice<K,N,V>>(1);
        if (qr != null) {
            ret.add(new Slice(query.getKey(), toJsonColumns(qr.get().getColumns())));
        }
        
        return ret;
    }
    
    @Override
    public <K, N, V> List<Slice<K,N,V>> keyRange(QxRangeQuery<K,N,V> query) {
        logQueryObject(query);
        RangeSlicesQuery<K, N, V> cq = HFactory.createRangeSlicesQuery(getKeyspace(query), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), getSerializer(query.getValueClass()));
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKeys(query.getKeyFrom(), query.getKeyTo());
        cq.setRowCount(query.getRowCount());
        
        if (ArrayUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(query.getNameStart(), query.getNameEnd(), query.isReversed(), query.getLimit());
        } else {
            cq.setColumnNames(query.getColumnNames());
        }
        
        QueryResult<OrderedRows<K, N, V>> qr = cq.execute();
        
        List<Slice<K,N,V>> ret = new ArrayList<Slice<K,N,V>>(1);
        if (qr != null && qr.get() != null) {
            List<Row<K, N, V>> rows = qr.get().getList();
            for (Row<K, N, V> row : rows) {
                ColumnSlice<N, V> colSlice = row.getColumnSlice();
                ret.add(new Slice(row.getKey(), toJsonColumns(colSlice.getColumns())));
            }
        }
        
        return ret;
    }
}