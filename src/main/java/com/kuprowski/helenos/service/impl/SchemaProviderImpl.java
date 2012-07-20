/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.service.SchemaProvider;
import com.kuprowski.helenos.types.JsonColumnFamilyDefinition;
import com.kuprowski.helenos.types.JsonKeyspaceDefinition;
import com.kuprowski.helenos.types.qx.QxJsonColumnFamilyDefinition;
import java.util.ArrayList;
import java.util.List;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomek
 */
@Component("schemaProvider")
public class SchemaProviderImpl extends AbstractProvider implements SchemaProvider {

    @Override
    public String describeClusterName() {
        return cluster.describeClusterName();
    }

    @Override
    public List<JsonKeyspaceDefinition> describeKeyspaces() {
        List<KeyspaceDefinition> x = cluster.describeKeyspaces();
        ArrayList<JsonKeyspaceDefinition> y = new ArrayList<JsonKeyspaceDefinition>();
        for (KeyspaceDefinition kd : x) {
            y.add(mapper.map(kd, JsonKeyspaceDefinition.class));
        }
        return y;
    }

    @Override
    public void dropKeyspace(String keyspaceName) {
        cluster.dropKeyspace(keyspaceName, true);
    }

    @Override
    public void dropColumnFamily(String keyspaceName, String columnFamily) {
        cluster.dropColumnFamily(keyspaceName, columnFamily, true);
    }

    @Override
    public void truncateColumnFamily(String keyspaceName, String columnFamily) {
        cluster.truncate(keyspaceName, columnFamily);
    }

    @Override
    public JsonKeyspaceDefinition describeKeyspace(String keyspaceName) {
        KeyspaceDefinition def = cluster.describeKeyspace(keyspaceName);
        return mapper.map(def, JsonKeyspaceDefinition.class);
    }

    @Override
    public JsonColumnFamilyDefinition describeColumnFamily(String keyspaceName, String columnFamilyName) {
        KeyspaceDefinition def = cluster.describeKeyspace(keyspaceName);
        JsonColumnFamilyDefinition jsonDef = null;
        for (ColumnFamilyDefinition thriftDef : def.getCfDefs()) {
            if (thriftDef.getName().equals(columnFamilyName)) {
                jsonDef = mapper.map(thriftDef, JsonColumnFamilyDefinition.class);
                continue;
            }
        }
        return jsonDef;
    }
    
    @Override
    public void createColumnFamily(QxJsonColumnFamilyDefinition qxDef) {
        ColumnFamilyDefinition cfDef = new ThriftCfDef(qxDef.getKeyspaceName(), qxDef.getName());
        cfDef.setGcGraceSeconds(qxDef.getGcGraceSeconds());
        cfDef.setComment(qxDef.getComment());
        cfDef.setColumnType(ColumnType.getFromValue(qxDef.getColumnType()));
        cfDef.setComparatorType(ComparatorType.getByClassName(qxDef.getComparatorType()));
        if (qxDef.getColumnType().equals(ColumnType.SUPER.getValue())) {
            cfDef.setSubComparatorType(ComparatorType.getByClassName(qxDef.getSubComparatorType()));
        }
        cfDef.setKeyValidationClass(qxDef.getKeyValidationclass());
        cfDef.setDefaultValidationClass(qxDef.getDefaultValidationclass());
        
        cluster.addColumnFamily(cfDef, true);
    }
}
