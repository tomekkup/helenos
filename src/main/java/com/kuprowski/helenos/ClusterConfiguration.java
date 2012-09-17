package com.kuprowski.helenos;

import java.util.HashMap;
import java.util.Map;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Cluster;
import org.springframework.util.Assert;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class ClusterConfiguration {

    private String hosts;
    private String clusterName;
    private boolean active;
    private String alias;

    public ClusterConfiguration() {
    }

    public ClusterConfiguration(String alas, String hosts, String clusterName, boolean active) {
        setAlias(alias);
        setHosts(hosts);
        setClusterName(clusterName);
        setActive(active);
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Cluster createCluster() {
        return new ThriftCluster(clusterName, new CassandraHostConfigurator(hosts));
    }

    public Map<String, ?> toParametersMap() {
        Assert.hasLength(hosts);
        Assert.hasLength(clusterName);

        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("alias", alias);
        map.put("hosts", hosts);
        map.put("clustername", clusterName);
        map.put("active", active);
        return map;
    }
}
