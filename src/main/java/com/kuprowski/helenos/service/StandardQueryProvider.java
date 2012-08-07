/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
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

    <K, N> String singleColumn(@JsonRpcParam("keyClass") Class<K> keyClass, @JsonRpcParam("valueClass") Class<N> valueClass, @JsonRpcParam("columnFamily") String keyspaceName, @JsonRpcParam("columnFamily") String columnFamily, @JsonRpcParam("key") K key, @JsonRpcParam("name") N name);
    
}
