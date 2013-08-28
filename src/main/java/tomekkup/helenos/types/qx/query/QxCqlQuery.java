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
public class QxCqlQuery<K,N,V> extends AbstractQuery<K,N,V> {

    private String query;

    public QxCqlQuery() {
        super();
    }

    public QxCqlQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily, String query) {
        super(keyClass, nameClass, keyspace, columnFamily);
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
