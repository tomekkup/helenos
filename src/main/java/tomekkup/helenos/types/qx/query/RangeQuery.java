package tomekkup.helenos.types.qx.query;

import java.util.List;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class RangeQuery<K,N,V> extends AbstractPredicateQuery<K,N,V> {

    private String keyFrom;
    private String keyTo;
    private String nameStart;
    private String nameEnd;
    private List<String> columnNames;

    public RangeQuery() {
        super();
    }

    public RangeQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, String keyFrom, String keyTo, List<String> columnNames, String nameStart, String nameEnd) {
        super(keyClass, nameClass, keyspace, columnFamily);
        setKeyFrom(keyFrom);
        setKeyTo(keyTo);
        setColumnNames(columnNames);
        setNameStart(nameStart);
        setNameEnd(nameEnd);
    }
    
    public void setKeyFrom(String keyFrom) {
        this.keyFrom = keyFrom;
    }

    public String getKeyFrom() {
        return keyFrom;
    }

    public void setKeyTo(String keyTo) {
        this.keyTo = keyTo;
    }

    public String getKeyTo() {
        return keyTo;
    }
    
    public String getNameStart() {
        return nameStart;
    }

    public void setNameStart(String nameStart) {
        this.nameStart = nameStart;
    }

    public String getNameEnd() {
        return nameEnd;
    }

    public void setNameEnd(String nameEnd) {
        this.nameEnd = nameEnd;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
