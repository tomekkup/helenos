/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.dozer.converters;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 *
 * @author tomek
 */
public class HeapByteBufferConverter implements CustomConverter {

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (!(sourceFieldValue instanceof java.nio.ByteBuffer)) {
            throw new MappingException("Converter " + this.getClass().toString() + " used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue + " and " + sourceFieldValue);
        }

        return Charset.forName("UTF-8").decode((ByteBuffer) sourceFieldValue).toString();
    }
}
