package com.kuprowski.helenos.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.kuprowski.helenos.Converter;
import com.kuprowski.helenos.service.StandardQueryProvider;
import com.kuprowski.helenos.types.SliceResult;
import com.kuprowski.helenos.types.qx.query.SingleColumnQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.text.StyledEditorKit;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hector.api.query.SubColumnQuery;
import org.springframework.stereotype.Component;

/**
 *
 * {
 * "id" : 1, "method" : "singleColumn", "params" : ["java.lang.String",
 * "java.lang.String", "lookminders", "Users", "mama", "userdata"] }
 */
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
public class StandardQueryProviderImpl extends AbstractProvider implements StandardQueryProvider {

    @Override
    public <K, N> String singleColumn(@JsonRpcParam("query") SingleColumnQuery<K, N> query) {
        ColumnQuery<K, N, String> cq = HFactory.createColumnQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(Converter.toValue(query.getKey(), query.getKeyClass()));
        cq.setName(Converter.toValue(query.getName(), query.getNameClass()));

        HColumn<N, String> column = cq.execute().get();
        return column != null ? column.getValue() : null;
    }
    
    @Override
    public <K, N> List<SliceResult<N>> slice(com.kuprowski.helenos.types.qx.query.SliceQuery<K, N> query) {
        SliceQuery<K, N, String> cq = HFactory.createSliceQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(Converter.toValue(query.getKey(), query.getKeyClass()));
        cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMax());
        
        QueryResult<ColumnSlice<N, String>> qr = cq.execute();
        
        List<SliceResult<N>> ret = new ArrayList<SliceResult<N>>();
        if (qr != null) {
            List<HColumn<N, String>> columns = qr.get().getColumns();
            for (HColumn<N, String> column : columns) {
                ret.add(new SliceResult(column));
            }
        }

        return ret;
    }
}
