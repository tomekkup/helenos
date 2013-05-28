package tomekkup.helenos.service;

import java.util.List;
import tomekkup.helenos.types.JsonColumnFamilyDefinition;
import tomekkup.helenos.types.JsonKeyspaceDefinition;

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

    List<JsonKeyspaceDefinition> describeKeyspaces();

    JsonKeyspaceDefinition describeKeyspace(String keyspaceName);

    JsonColumnFamilyDefinition describeColumnFamily(String keyspaceName, String columnFamilyName);
}
