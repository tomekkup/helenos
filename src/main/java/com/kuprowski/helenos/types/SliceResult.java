/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.types;

import me.prettyprint.hector.api.beans.HColumn;

/**
 *
 * @author tomek
 */

/*****
 * ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE 
 * ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE 
 * ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE ZMIEN NAZWE 
 * @author tomek
 * @param <N> 
 */
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
