package tomekkup.helenos.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import tomekkup.helenos.types.Column;
import tomekkup.helenos.types.Slice;
import tomekkup.helenos.types.qx.query.SingleSubColumnQuery;
import tomekkup.helenos.types.qx.query.SubRangeQuery;
import java.util.List;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface SuperQueryProvider {

    <K, SN, N> Column<N> singleColumn(@JsonRpcParam("query") SingleSubColumnQuery<K, SN, N> query);

    <K, SN, N> List<Slice<K,N>> predicate(@JsonRpcParam("query") SubRangeQuery<K, SN, N> query);
    
    <K, SN, N> List<Slice<K,N>> keyRange(tomekkup.helenos.types.qx.query.SubRangeQuery<K, SN, N> query);
}
