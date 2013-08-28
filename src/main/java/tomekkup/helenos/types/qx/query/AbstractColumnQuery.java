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
public abstract class AbstractColumnQuery<K, N, V> extends AbstractQuery<K, N, V> {

    public static final int DEFAULT_COLUMNS_LIMIT = 10;
    private List<String> columnNames;
    private String nameStart;
    private String nameEnd;
    private int limit = DEFAULT_COLUMNS_LIMIT;
    private boolean reversed = false;
    
    public AbstractColumnQuery() {
        super();
    }
    
    public AbstractColumnQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, List<String> columnNames) {
        super(keyClass, nameClass, keyspace, columnFamily);
        this.columnNames = columnNames;
    }
    
    public AbstractColumnQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, String nameStart, String nameEnd, int limit, boolean reversed) {
        super(keyClass, nameClass, keyspace, columnFamily);
        this.nameStart = nameStart;
        this.nameEnd = nameEnd;
        this.limit = limit;
        this.reversed = reversed;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public N[] getColumnNames() {
        return Converter.toValue(this.columnNames, getNameClass());
    }

    public N getNameStart() {
        return Converter.toValue(nameStart, getNameClass());
    }

    public void setNameStart(String nameStart) {
        this.nameStart = nameStart;
    }

    public N getNameEnd() {
        return Converter.toValue(nameEnd, getNameClass());
    }

    public void setNameEnd(String nameEnd) {
        this.nameEnd = nameEnd;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }
}
