package tomekkup.helenos.types;

import java.util.List;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class Slice<K,N,V> {

    private K key;
    private List<Column<N,V>> columns;

    public Slice() {
        super();
    }
    
    public Slice(K key, List<Column<N,V>> columns) {
        setKey(key);
        setColumns(columns);
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public List<Column<N,V>> getColumns() {
        return columns;
    }

    public void setColumns(List<Column<N,V>> columns) {
        this.columns = columns;
    }
}
