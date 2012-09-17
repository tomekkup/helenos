package com.kuprowski.helenos;

import java.util.UUID;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class Converter {

    public static <T> T toValue(String stringVal, Class<T> clazz) {
        if (stringVal == null) {
            return null;
        }
        if (String.class.equals(clazz)) {
            return (T) stringVal;
        }
        if (Long.class.equals(clazz)) {
            return (T) new Long(stringVal);
        }
        if (UUID.class.equals(clazz)) {
            return (T) UUID.fromString(stringVal);
        }

        throw new IllegalArgumentException("class not recognized: " + clazz);
    }
    
    
}
