package tomekkup.helenos.service;

import tomekkup.helenos.types.qx.QxJsonColumnFamilyDefinition;
import tomekkup.helenos.types.qx.QxJsonKeyspaceDefinition;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface SchemaAdminProvider {
    
    void dropKeyspace(String keyspaceName);

    void truncateColumnFamily(String keyspaceName, String columnFamily);

    void dropColumnFamily(String keyspaceName, String columnFamily);
    
    void createColumnFamily(QxJsonColumnFamilyDefinition qxDef);

    void createKeyspace(QxJsonKeyspaceDefinition qxDef);
}
