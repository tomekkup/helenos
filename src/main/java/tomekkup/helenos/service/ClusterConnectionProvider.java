package tomekkup.helenos.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import me.prettyprint.cassandra.service.CassandraHost;
import tomekkup.helenos.dao.model.ClusterConfiguration;
import tomekkup.helenos.types.qx.QxClusterConfiguration;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface ClusterConnectionProvider {

    List<ClusterConfiguration> loadAll();
    
    void create(QxClusterConfiguration configuration);
    
    ClusterConfiguration getConnectionByAlias(String alias);
    
    long getConnectionsCount();

    void store(QxClusterConfiguration configuration);

    void delete(String alias);

    void activate(String alias);

    Map<String, Set<CassandraHost>> getConnectionStatus();
}
