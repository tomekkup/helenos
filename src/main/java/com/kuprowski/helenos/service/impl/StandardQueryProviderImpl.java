package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.service.StandardQueryProvider;
import java.util.UUID;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.SubColumnQuery;
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
@Component("standardQueryProvider")
public class StandardQueryProviderImpl extends AbstractProvider implements StandardQueryProvider {

    @Override
    public <K,N> String singleColumn(Class<K> keyClass, Class<N> nameClass, String keyspaceName, String columnFamily, K key, N name) {
        ColumnQuery<K,N,String> query = HFactory.createColumnQuery(getKeyspace(keyspaceName), getSerializer(keyClass), getSerializer(nameClass), StringSerializer.get());
        query.setColumnFamily(columnFamily);
        query.setKey(key);
        query.setName(name);
        
        HColumn<N, String> column = query.execute().get();
        return column != null ? column.getValue() : null;
    }
}
