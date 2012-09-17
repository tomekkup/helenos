package com.kuprowski.helenos.service;

import com.kuprowski.helenos.ClusterConfiguration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.prettyprint.cassandra.service.CassandraHost;

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

    void store(ClusterConfiguration configuration);

    void delete(String hosts);

    void activate(ClusterConfiguration configuration);

    Map<String, Set<CassandraHost>> getConnectionStatus();
}
