/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service;

import com.kuprowski.helenos.types.JsonColumnFamilyDefinition;
import com.kuprowski.helenos.types.JsonKeyspaceDefinition;
import com.kuprowski.helenos.types.qx.QxJsonColumnFamilyDefinition;
import java.util.List;

/**
 *
 * @author tomek
 */
public interface SchemaProvider {
    
    String describeClusterName();
    
    void dropKeyspace(String keyspaceName);
    
    void truncateColumnFamily(String keyspaceName, String columnFamily);
    
    void dropColumnFamily(String keyspaceName, String columnFamily);
    
    List<JsonKeyspaceDefinition> describeKeyspaces();
    
    JsonKeyspaceDefinition describeKeyspace(String keyspaceName);
    
    JsonColumnFamilyDefinition describeColumnFamily(String keyspaceName, String columnFamilyName);
    
    void createColumnFamily(QxJsonColumnFamilyDefinition qxDef);
}
