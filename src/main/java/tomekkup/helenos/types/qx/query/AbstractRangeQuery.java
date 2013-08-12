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
public abstract class AbstractRangeQuery<K,N,V> extends AbstractQuery<K,N,V> {
    
    private static final int DEFAULT_LIMIT = 10;
    protected int limit = DEFAULT_LIMIT;

    public AbstractRangeQuery() {
    }
    
    public AbstractRangeQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily) {
        super(keyClass, nameClass, keyspace, columnFamily);
    }
    
    public AbstractRangeQuery(int limit) {
        setLimit(limit);
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return this.limit;
    }
}
