/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.types.qx.query;

/**
 *
 * @author tomek
 */
public class SingleColumnQuery<K, N> extends AbstractQuery<K, N> {

    private String key;
    private String name;

    public SingleColumnQuery() {
        super();
    }

    public SingleColumnQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, String key, String name) {
        super(keyClass, nameClass, keyspace, columnFamily);
        setKey(key);
        setName(name);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
