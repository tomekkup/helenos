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
public class QxRangeQuery<K,N,V> extends AbstractColumnQuery<K,N,V> {
    
    public static final int DEFAULT_ROW_COUNT = 10;
    private String keyFrom;
    private String keyTo;
    private int rowCount = DEFAULT_ROW_COUNT;

    public QxRangeQuery() {
        super();
    }
    
    public QxRangeQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, List<String> columnNames, String keyFrom, String keyTo, int rowCount) {
        super(keyClass, nameClass, keyspace, columnFamily, columnNames);
        this.keyFrom = keyFrom;
        this.keyTo = keyTo;
        this.rowCount = rowCount;
    }
    
    public QxRangeQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, String nameStart, String nameEnd, int colLimit, boolean reversed, String keyFrom, String keyTo, int rowCount) {
        super(keyClass, nameClass, keyspace, columnFamily, nameStart, nameEnd, colLimit, reversed);
        this.keyFrom = keyFrom;
        this.keyTo = keyTo;
        this.rowCount = rowCount;
    }

    public K getKeyFrom() {
        return Converter.toValue(keyFrom, getKeyClass());
    }

    public void setKeyFrom(String keyFrom) {
        this.keyFrom = keyFrom;
    }

    public K getKeyTo() {
        return Converter.toValue(keyTo, getKeyClass());
    }

    public void setKeyTo(String keyTo) {
        this.keyTo = keyTo;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
