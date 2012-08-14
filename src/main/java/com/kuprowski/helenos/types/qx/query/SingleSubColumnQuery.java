/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.types.qx.query;

/**
 *
 * @author tomek
 */
public class SingleSubColumnQuery<K,SN,N> extends SingleColumnQuery<K, N> {

    private Class<SN> sNameClass;
    private String sName;
    

    public SingleSubColumnQuery() {
        super();
    }

    public SingleSubColumnQuery(Class<K> keyClass, Class<SN> sNameClass, Class<N> nameClass, String keyspace, String columnFamily, String key, String sName, String name) {
        super(keyClass, nameClass, keyspace, columnFamily, key, name);
        setsNameClass(sNameClass);
        setsName(sName);;
    }

    public Class<SN> getsNameClass() {
        return sNameClass;
    }

    public void setsNameClass(Class<SN> sNameClass) {
        this.sNameClass = sNameClass;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    
}
