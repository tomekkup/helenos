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
public class SingleSubColumnQuery<K,SN,N,V> extends SingleColumnQuery<K,N,V> {

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
