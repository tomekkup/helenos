package com.kuprowski.helenos.types;

import me.prettyprint.hector.api.beans.HColumn;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */

/* TODO change name */
public class SliceResult<N> {
    
    private N name;
    private String value;

    public SliceResult() {
        
    }
    
    public SliceResult(HColumn<N, String> column) {
        setName(column.getName());
        setValue(column.getValue());
    }
    
    public void setName(N name) {
        this.name = name;
    }

    public N getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
