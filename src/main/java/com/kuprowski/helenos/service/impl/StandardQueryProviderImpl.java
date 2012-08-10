package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.Converter;
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
 *
{
"id" : 1,
"method" : "singleColumn",
"params" : ["java.lang.String", "java.lang.String", "lookminders", "Users", "mama", "userdata"]
}
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
    public <K,N> String singleColumn(Class<K> keyClass, Class<N> nameClass, String keyspaceName, String columnFamily, String keyStr, String nameStr) {
        ColumnQuery<K,N,String> query = HFactory.createColumnQuery(getKeyspace(keyspaceName), getSerializer(keyClass), getSerializer(nameClass), StringSerializer.get()); 
        query.setColumnFamily(columnFamily);
        query.setKey(Converter.toValue(keyStr, keyClass));
        query.setName(Converter.toValue(nameStr, nameClass));
        
        HColumn<N, String> column = query.execute().get();
        return column != null ? column.getValue() : null;
    }
}
