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
public abstract class AbstractPredicateQuery<K,N> extends AbstractRangeQuery<K,N> {
    
    private static final int DEFAULT_MAX_KEYS = 10;
    private boolean reversed = true;
    private int maxKeys = DEFAULT_MAX_KEYS;
    
    public AbstractPredicateQuery() {
    }
    
    public AbstractPredicateQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily) {
        super(keyClass, nameClass, keyspace, columnFamily);
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public void setMaxKeys(int maxKeys) {
        this.maxKeys = maxKeys;
    }

    public int getMaxKeys() {
        return maxKeys;
    }
}
