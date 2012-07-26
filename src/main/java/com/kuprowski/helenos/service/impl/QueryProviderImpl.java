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
 */
/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
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
