/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.service.QueryProvider;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * query example:
 {
    "id": 1,
    "jsonrpc": "2.0",
    "method": "get",
    "params": {
        "cf": "none",
        "uuidKey": "550e8400-e29b-41d4-a716-446655440000",
        "column": "none"
    }
 }
 * @author tomek
 */
@Component("queryProvider")
public class QueryProviderImpl extends AbstractProvider implements QueryProvider {

    @Override
    public String get(String columnFamily, UUID key, String column) {
        return "UUID xxx";
    }
    
    @Override
    public String get(String columnFamily, String key, String column) {
        return "String xxx";
    }    
    
    
}
