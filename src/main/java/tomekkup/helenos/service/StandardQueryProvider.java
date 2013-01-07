package tomekkup.helenos.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import java.util.List;
import tomekkup.helenos.types.Column;
import tomekkup.helenos.types.Slice;
import tomekkup.helenos.types.qx.query.CqlQuery;
import tomekkup.helenos.types.qx.query.RangeQuery;
import tomekkup.helenos.types.qx.query.SingleColumnQuery;

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
    <K, N, V> List<Slice<K, N, V>> predicate(@JsonRpcParam("query") RangeQuery<K, N, V> query);

    <K, N, V> List<Slice<K, N, V>> keyRange(tomekkup.helenos.types.qx.query.RangeQuery<K, N, V> query);

    <K, N, V> List<Slice<K, N, V>> cql(@JsonRpcParam("query") CqlQuery<K, N, V> query);
}
