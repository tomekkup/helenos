package tomekkup.helenos.service.impl;

import java.util.ArrayList;
import java.util.List;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import org.springframework.stereotype.Component;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.SchemaProvider;
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
@Component("schemaProvider")
public class SchemaProviderImpl extends AbstractProvider implements SchemaProvider, ClusterConfigAware {

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
}
