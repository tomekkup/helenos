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
public abstract class AbstractSliceQuery<K,N> extends AbstractQuery<K,N> {
    
    private int max = 20;
    private boolean reversed = true;
    
    public AbstractSliceQuery() {
    }
    
    public AbstractSliceQuery(Class<K> keyClass, Class<N> nameClass, String keyspace, String columnFamily) {
        super(keyClass, nameClass, keyspace, columnFamily);
    }
    
    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }
    
}
