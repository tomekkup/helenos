/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.types.qx.query;

/**
 *
 * @author tomek
 */
public class SliceQuery<K, N> extends AbstractSliceQuery<K, N> {

    private String key;
    private String nameStart;
    private String nameEnd;

    public SliceQuery() {
        super();
    }

    public SliceQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, String key, String nameStart, String nameEnd) {
        super(keyClass, nameClass, keyspace, columnFamily);
        setKey(key);
        setNameStart(nameStart);
        setNameEnd(nameEnd);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

  
}
