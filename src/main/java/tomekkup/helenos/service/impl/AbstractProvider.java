package tomekkup.helenos.service.impl;

import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.factory.HFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import tomekkup.helenos.cassandra.model.AllConsistencyLevelPolicy;

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
        Assert.notNull(cluster, "connection not ready yet");
        return HFactory.createKeyspace(keyspaceName, cluster, consistencyLevelPolicy);
    }
    
    protected Keyspace getKeyspace(String keyspaceName, String consistencyLevelStr) {
        Assert.notNull(cluster, "connection not ready yet");
        
        return HFactory.createKeyspace(keyspaceName, cluster, this.resolveCLP(consistencyLevelStr));
    }
    
    private ConsistencyLevelPolicy resolveCLP(String consistencyLevelStr) {
        if ("ONE".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.ONE);
        } else
        if ("TWO".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.TWO);
        } else
        if ("THREE".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.THREE);
        } else
        if ("QUORUM".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.QUORUM);
        } else
        if ("ALL".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.ALL);
        } else
        if ("ANY".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.ANY);
        } else
        if ("LOCAL_QUORUM".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.LOCAL_QUORUM);
        } else
        if ("EACH_QUORUM".equals(consistencyLevelStr)) {
            return AllConsistencyLevelPolicy.getInstance(HConsistencyLevel.EACH_QUORUM);
        }
        throw new IllegalStateException("unknown consistency level");
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
