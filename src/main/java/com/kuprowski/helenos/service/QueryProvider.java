/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.kuprowski.helenos.types.JsonColumnFamilyDefinition;
import com.kuprowski.helenos.types.JsonKeyspaceDefinition;
import com.kuprowski.helenos.types.qx.QxJsonColumnFamilyDefinition;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author tomek
 */
public interface QueryProvider {
    
    String get(String columnFamily, UUID uuidKey, String column);
    String get(String columnFamily, String stringKey, String column);
    
    String getUK(@JsonRpcParam("cf") String columnFamily, @JsonRpcParam("key") UUID key, @JsonRpcParam("column") String column);
}
