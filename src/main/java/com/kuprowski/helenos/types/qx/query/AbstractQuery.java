/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.types.qx.query;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public abstract class AbstractQuery<K,N> {

    public AbstractQuery() {
    }
    
    public AbstractQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily) {
        setKeyClass(keyClass);
        setNameClass(nameClass);
        setKeyspace(keyspace);
        setColumnFamily(columnFamily);
    }
    
    private String keyspace;
    private String columnFamily;
    
    private Class<K> keyClass;
    private Class<N> nameClass;

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
}
