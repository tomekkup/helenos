package tomekkup.helenos.types.qx.query;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public abstract class AbstractQuery<K, N, V> implements Query<K, N, V> {

    private String keyspace;
    private String columnFamily;
    private Class<K> keyClass;
    private Class<N> nameClass;
    private Class<V> valueClass;
    private String consistencyLevel;
    
    public AbstractQuery() {
    }

    public AbstractQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily) {
        this.keyClass = keyClass;
        this.nameClass = nameClass;
        this.keyspace = keyspace;
        this.columnFamily = columnFamily;
    }

    @Override
    public String getConsistencyLevel() {
        return consistencyLevel;
    }

    public void setConsistencyLevel(String consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
    }

    @Override
    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public void setKeyClass(Class<K> keyClass) {
        this.keyClass = keyClass;
    }

    public Class<K> getKeyClass() {
        return keyClass;
    }

    public void setNameClass(Class<N> nameClass) {
        this.nameClass = nameClass;
    }

    public Class<N> getNameClass() {
        return nameClass;
    }

    public void setValueClass(Class<V> valueClass) {
        this.valueClass = valueClass;
    }

    public Class<V> getValueClass() {
        return valueClass;
    }
}
