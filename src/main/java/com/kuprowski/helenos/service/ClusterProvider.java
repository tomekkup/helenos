/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service;

import com.kuprowski.helenos.types.JsonColumnFamilyDefinition;
import com.kuprowski.helenos.types.JsonKeyspaceDefinition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomek
 */
public interface ClusterProvider {
    
    String describeClusterName();
    
    List<JsonKeyspaceDefinition> describeKeyspaces();
    
    JsonKeyspaceDefinition describeKeyspace(String keyspaceName);
    
    JsonColumnFamilyDefinition describeColumnFamily(String keyspaceName, String columnFamilyName);
}
