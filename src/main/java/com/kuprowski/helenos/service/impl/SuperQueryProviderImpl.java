package com.kuprowski.helenos.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.kuprowski.helenos.Converter;
import com.kuprowski.helenos.service.StandardQueryProvider;
import com.kuprowski.helenos.service.SuperQueryProvider;
import com.kuprowski.helenos.types.SliceResult;
import com.kuprowski.helenos.types.qx.query.SingleColumnQuery;
import com.kuprowski.helenos.types.qx.query.SingleSubColumnQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.*;
import org.springframework.stereotype.Component;

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
public class SuperQueryProviderImpl extends AbstractProvider implements SuperQueryProvider {

    @Override
    public <K, SN, N> String singleColumn(@JsonRpcParam("query") SingleSubColumnQuery<K, SN, N> query) {
        SubColumnQuery<K, SN, N, String> cq = HFactory.createSubColumnQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(Converter.toValue(query.getKey(), query.getKeyClass()));
        cq.setSuperColumn(Converter.toValue(query.getsName(), query.getsNameClass()));
        cq.setColumn(Converter.toValue(query.getName(), query.getNameClass()));

        HColumn<N, String> column = cq.execute().get();
        return column != null ? column.getValue() : null;
    }

    @Override
    public <K, SN, N> List<SliceResult<N>> slice(com.kuprowski.helenos.types.qx.query.SubSliceQuery<K, SN, N> query) {
        SubSliceQuery<K, SN, N, String> cq = HFactory.createSubSliceQuery(getKeyspace(query.getKeyspace()), getSerializer(query.getKeyClass()), getSerializer(query.getsNameClass()), getSerializer(query.getNameClass()), StringSerializer.get());
        cq.setColumnFamily(query.getColumnFamily());
        cq.setKey(Converter.toValue(query.getKey(), query.getKeyClass()));
        cq.setSuperColumn(Converter.toValue(query.getsName(), query.getsNameClass()));
        cq.setRange(Converter.toValue(query.getNameStart(), query.getNameClass()), Converter.toValue(query.getNameEnd(), query.getNameClass()), query.isReversed(), query.getMax());

        QueryResult<ColumnSlice<N, String>> qr =  cq.execute();
        
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
