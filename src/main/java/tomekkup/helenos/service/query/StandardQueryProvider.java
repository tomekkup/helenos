package tomekkup.helenos.service.query;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import java.util.List;
import tomekkup.helenos.types.Slice;
import tomekkup.helenos.types.qx.query.QxCqlQuery;
import tomekkup.helenos.types.qx.query.QxPredicateQuery;
import tomekkup.helenos.types.qx.query.QxRangeQuery;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface StandardQueryProvider {
    <K, N, V> List<Slice<K, N, V>> predicate(@JsonRpcParam("query") QxPredicateQuery<K, N, V> query);

    <K, N, V> List<Slice<K, N, V>> keyRange(@JsonRpcParam("query") QxRangeQuery<K, N, V> query);

    <K, N, V> List<Slice<K, N, V>> cql(@JsonRpcParam("query") QxCqlQuery<K, N, V> query);
}
