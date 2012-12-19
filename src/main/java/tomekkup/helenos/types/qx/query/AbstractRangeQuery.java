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
public abstract class AbstractRangeQuery<K,N> extends AbstractQuery<K,N> {
    
    private static final int DEFAULT_MAX_RESULTS = 20;
    protected int maxResults = DEFAULT_MAX_RESULTS;

    public AbstractRangeQuery() {
    }
    
    public AbstractRangeQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily) {
        super(keyClass, nameClass, keyspace, columnFamily);
    }
    
    public AbstractRangeQuery(int maxResults) {
        setMaxResults(maxResults);
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getMaxResults() {
        return maxResults;
    }
}
