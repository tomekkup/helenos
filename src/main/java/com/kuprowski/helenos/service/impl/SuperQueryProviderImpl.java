package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.Converter;
import com.kuprowski.helenos.service.StandardQueryProvider;
import com.kuprowski.helenos.service.SuperQueryProvider;
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
@Component("superQueryProvider")
public class SuperQueryProviderImpl extends AbstractProvider implements SuperQueryProvider {
      
    @Override
    public <K,SN, N> String singleColumn(Class<K> keyClass, Class<SN> sNameClass, Class<N> nameClass, String keyspaceName, String columnFamily, String keyStr, String sNameStr, String nameStr) {
        SubColumnQuery<K,SN,N,String> query = HFactory.createSubColumnQuery(getKeyspace(keyspaceName), getSerializer(keyClass), getSerializer(sNameClass), getSerializer(nameClass), StringSerializer.get());
        query.setColumnFamily(columnFamily);
        query.setKey(Converter.toValue(keyStr, keyClass));
        query.setSuperColumn(Converter.toValue(sNameStr, sNameClass));
        query.setColumn(Converter.toValue(nameStr, nameClass));
        
        HColumn<N, String> column2 = query.execute().get();
        return column2 != null ? column2.getValue() : null;
    }
    
}
