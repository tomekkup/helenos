package com.kuprowski.helenos.dozer.converters;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class HeapByteBufferConverter implements CustomConverter {
    
    private static final Charset charset = Charset.forName("UTF-8");

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (!(sourceFieldValue instanceof java.nio.ByteBuffer)) {
            throw new MappingException("Converter " + this.getClass().toString() + " used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue + " and " + sourceFieldValue);
        }

        return charset.decode((ByteBuffer) sourceFieldValue).toString();
    }
}
