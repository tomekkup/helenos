package tomekkup.helenos.utils;

import java.util.List;
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
    
    public static <T> T[] toValue(List<String> arrayVal, Class<T> clazz) {
        if (arrayVal == null) {
            return null;
        }
        if (String.class.equals(clazz)) {
            return (T[]) arrayVal.toArray(new String[0]);
        }
        if (Long.class.equals(clazz)) {
            return (T[]) arrayVal.toArray(new Long[0]);
        }
        if (UUID.class.equals(clazz)) {
            UUID[] temp = new UUID[arrayVal.size()];
            for (int i = 0; i < arrayVal.size(); i++) {
                temp[i] = UUID.fromString(arrayVal.get(i));
            }
            return (T[]) temp;
        }

        throw new IllegalArgumentException("class not recognized: " + clazz);
    }
}
