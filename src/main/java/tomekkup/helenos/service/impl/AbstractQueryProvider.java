package tomekkup.helenos.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.prettyprint.hector.api.beans.HColumn;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import tomekkup.helenos.types.Column;
import tomekkup.helenos.types.qx.query.Query;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public abstract class AbstractQueryProvider extends AbstractProvider {
    
    protected Logger logger;

    protected void logQueryObject(Query<?,?,?> queryObject) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, queryObject);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        writer.flush();
        logger.info(String.format("%s executed: %s", userName, writer.toString()));
    }
    
    protected <N, V> List<Column<N, V>> toJsonColumns(List<HColumn<N, V>> columns) {
        List<Column<N, V>> retColumns = new ArrayList<Column<N, V>>(1);
        Iterator<HColumn<N, V>> iterator = columns.iterator();
        while (iterator.hasNext()) {
            retColumns.add(mapper.map(iterator.next(), Column.class));
        }
        return retColumns;
    }
}
