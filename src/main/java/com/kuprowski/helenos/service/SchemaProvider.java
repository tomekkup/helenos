package com.kuprowski.helenos.service;

import com.kuprowski.helenos.types.JsonColumnFamilyDefinition;
import com.kuprowski.helenos.types.JsonKeyspaceDefinition;
import com.kuprowski.helenos.types.qx.QxJsonColumnFamilyDefinition;
import com.kuprowski.helenos.types.qx.QxJsonKeyspaceDefinition;
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
public interface SchemaProvider {

    String describeClusterName();

    void dropKeyspace(String keyspaceName);

    void truncateColumnFamily(String keyspaceName, String columnFamily);

    void dropColumnFamily(String keyspaceName, String columnFamily);

    List<JsonKeyspaceDefinition> describeKeyspaces();

    JsonKeyspaceDefinition describeKeyspace(String keyspaceName);

    JsonColumnFamilyDefinition describeColumnFamily(String keyspaceName, String columnFamilyName);

    void createColumnFamily(QxJsonColumnFamilyDefinition qxDef);

    void createKeyspace(QxJsonKeyspaceDefinition qxDef);
}
