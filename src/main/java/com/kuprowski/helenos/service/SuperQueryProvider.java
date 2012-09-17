package com.kuprowski.helenos.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.kuprowski.helenos.types.SliceResult;
import com.kuprowski.helenos.types.qx.query.SingleSubColumnQuery;
import com.kuprowski.helenos.types.qx.query.SubSliceQuery;
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

    <K, SN, N> String singleColumn(@JsonRpcParam("query") SingleSubColumnQuery<K, SN, N> query);

    <K, SN, N> List<SliceResult<N>> slice(@JsonRpcParam("query") SubSliceQuery<K, SN, N> query);
}
