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
public class SingleColumnQuery<K,N,V> extends AbstractQuery<K,N,V> {

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
