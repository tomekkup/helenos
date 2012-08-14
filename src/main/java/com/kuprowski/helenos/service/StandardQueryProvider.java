/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.kuprowski.helenos.types.SliceResult;
import com.kuprowski.helenos.types.qx.query.SingleColumnQuery;
import com.kuprowski.helenos.types.qx.query.SliceQuery;
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
public interface StandardQueryProvider {

    //<K, N> String singleColumn(@JsonRpcParam("keyClass") Class<K> keyClass, @JsonRpcParam("valueClass") Class<N> valueClass, @JsonRpcParam("columnFamily") String keyspaceName, @JsonRpcParam("columnFamily") String columnFamily, @JsonRpcParam("key") String keyStr, @JsonRpcParam("name") String nameStr);
    
    <K, N> String singleColumn(@JsonRpcParam("query") SingleColumnQuery<K,N> query);
    <K, N> List<SliceResult<N>> slice(@JsonRpcParam("query") SliceQuery<K,N> query);
}
