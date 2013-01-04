package tomekkup.helenos.dozer.converters;

import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class SimpleGrantedAuthorityConverter implements CustomConverter {
    
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (!(sourceFieldValue instanceof String)) {
            throw new MappingException("Converter " + this.getClass().toString() + " used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue + " and " + sourceFieldValue);
        }

        return new SimpleGrantedAuthority((String)sourceFieldValue);
    }
}
