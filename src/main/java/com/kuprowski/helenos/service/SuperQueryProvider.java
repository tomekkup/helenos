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
public interface SuperQueryProvider {
    
    <K,SN, N> String singleColumn(@JsonRpcParam("keyClass") Class<K> keyClass, @JsonRpcParam("sNameClass") Class<SN> sNameClass, @JsonRpcParam("nameClass") Class<N> nameClass, @JsonRpcParam("keyspaceName") String keyspaceName, @JsonRpcParam("columnFamily") String columnFamily, @JsonRpcParam("key") K key, @JsonRpcParam("superColumn") SN supercolumn, @JsonRpcParam("column") N column);
}
