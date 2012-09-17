package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.ClusterConfiguration;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.factory.HFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public abstract class AbstractProvider {

    @Autowired
    protected Mapper mapper;
    @Autowired
    protected ConsistencyLevelPolicy consistencyLevelPolicy;
    protected Cluster cluster;

    protected <V> Serializer<V> getSerializer(Class<V> clazz) {
        Serializer<V> serializer = SerializerTypeInferer.getSerializer(clazz);
        if (serializer.getClass().equals(ObjectSerializer.class)) {
            throw new IllegalStateException("can not obtain correct serializer for class: " + clazz);
        }
        return serializer;
    }

    protected Keyspace getKeyspace(String keyspaceName) {
        if (cluster == null) {
            throw new IllegalStateException("not ready yet");
        }
        return HFactory.createKeyspace(keyspaceName, cluster, consistencyLevelPolicy);
    }

    @Required
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Required
    public void setConsistencyLevelPolicy(ConsistencyLevelPolicy consistencyLevelPolicy) {
        this.consistencyLevelPolicy = consistencyLevelPolicy;
    }
    
    public final void setNewCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
