/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service.impl;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.kuprowski.helenos.service.QueryProvider;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomek
 */
@Component("queryProvider")
public class QueryProviderImpl extends AbstractProvider implements QueryProvider {

    @Override
    public String get(String columnFamily, UUID uuidKey, String column) {
        return "UUID xxx";
    }
    
    @Override
    public String get(String columnFamily, String stringKey, String column) {
        return "String xxx";
    }

    
    
    @Override
    public String getUK(@JsonRpcParam("cf") String columnFamily, UUID key, String column) {
        return "UUID yyy";
    }
}
