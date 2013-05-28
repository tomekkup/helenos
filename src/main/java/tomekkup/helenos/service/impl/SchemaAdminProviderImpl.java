package tomekkup.helenos.service.impl;

import java.util.ArrayList;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import org.springframework.stereotype.Component;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.SchemaAdminProvider;
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
@Component("schemaAdminProvider")
public class SchemaAdminProviderImpl extends AbstractProvider implements SchemaAdminProvider, ClusterConfigAware {

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

    @Override
    public void createKeyspace(QxJsonKeyspaceDefinition qxDef) {
        KeyspaceDefinition ksDef = new ThriftKsDef(qxDef.getKeyspaceName(), qxDef.getStrategyClass(), qxDef.getReplicationFactor(), new ArrayList<ColumnFamilyDefinition>(0));

        cluster.addKeyspace(ksDef, true);
    }
}
