package tomekkup.helenos.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.prettyprint.hector.api.beans.HColumn;
import tomekkup.helenos.types.Column;

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

    protected <N, V> List<Column<N, V>> toJsonColumns(List<HColumn<N, V>> columns) {
        List<Column<N, V>> retColumns = new ArrayList<Column<N, V>>(1);
        Iterator<HColumn<N, V>> iterator = columns.iterator();
        while (iterator.hasNext()) {
            retColumns.add(mapper.map(iterator.next(), Column.class));
        }
        return retColumns;
    }
}
