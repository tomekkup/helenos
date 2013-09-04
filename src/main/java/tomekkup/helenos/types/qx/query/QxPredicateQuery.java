package tomekkup.helenos.types.qx.query;

import java.util.List;
import tomekkup.helenos.Converter;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class QxPredicateQuery<K,N,V> extends AbstractColumnQuery<K,N,V> {
    
    private String key;
    
    public QxPredicateQuery() {
        super();
    }
    
    public QxPredicateQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, List<String> columnNames, String key) {
        super(keyClass, nameClass, keyspace, columnFamily, columnNames);
        this.key = key;
    }
    
    public QxPredicateQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, String nameStart, String nameEnd, int colLimit, boolean reversed, String key) {
        super(keyClass, nameClass, keyspace, columnFamily, nameStart, nameEnd, colLimit, reversed);
        this.key = key;
    }

    public K getKey() {
        return Converter.toValue(key, getKeyClass());
    }

    public void setKey(String key) {
        this.key = key;
    }
}
