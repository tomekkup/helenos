package tomekkup.helenos.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import tomekkup.helenos.Converter;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.SuperQueryProvider;
import tomekkup.helenos.types.Column;
import tomekkup.helenos.types.Slice;
import tomekkup.helenos.types.qx.query.SingleSubColumnQuery;
import java.util.ArrayList;
import java.util.List;
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
    public <K, SN, N> Column<N> singleColumn(@JsonRpcParam("query") SingleSubColumnQuery<K, SN, N> query) {
        SubColumnQuery<K, SN, N, String> cq = HFactory.createSubColumnQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(Converter.toValue(query.getKey(), query.getKeyClass()));
        cq.setSuperColumn(Converter.toValue(query.getsName(), query.getsNameClass()));
        cq.setColumn(Converter.toValue(query.getName(), query.getNameClass()));

        HColumn<N, String> column = cq.execute().get();
        return mapper.map(column, Column.class);
    }

    @Override
    public <K, SN, N> List<Slice<K,N>> predicate(tomekkup.helenos.types.qx.query.SubRangeQuery<K, SN, N> query) {
        K key = Converter.toValue(query.getKeyFrom(), query.getKeyClass());
        SubSliceQuery<K, SN, N, String> cq = HFactory.createSubSliceQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(key);
        cq.setSuperColumn(Converter.toValue(query.getsName(), query.getsNameClass()));
        if (CollectionUtils.isEmpty(query.getColumnNames())) {
            cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMaxResults());
        } else {
            cq.setColumnNames(Converter.toValue(query.getColumnNames(), query.getNameClass()));
        }
        
        QueryResult<ColumnSlice<N, String>> qr = cq.execute();
        
        List<Slice<K,N>> ret = new ArrayList<Slice<K,N>>(1);
        if (qr != null) {
            ret.add(new Slice(key, toJsonColumns(qr.get().getColumns())));
        }
        
        return ret;
    }
    
    @Override
    public <K, SN, N> List<Slice<K,N>> keyRange(tomekkup.helenos.types.qx.query.SubRangeQuery<K, SN, N> query) {
        RangeSubSlicesQuery<K, SN, N, String> cq = HFactory.createRangeSubSlicesQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        
        cq.setKeys(Converter.toValue(query.getKeyFrom(), query.getKeyClass()), Converter.toValue(query.getKeyTo(), query.getKeyClass()));
        cq.setSuperColumn(Converter.toValue(query.getsName(), query.getsNameClass()));
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
