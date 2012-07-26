/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service.impl;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
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
    protected Cluster cluster;

    @Autowired
    protected ConsistencyLevelPolicy consistencyLevelPolicy;
    
    @Required
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Required
    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    @Required
    public void setConsistencyLevelPolicy(ConsistencyLevelPolicy consistencyLevelPolicy) {
        this.consistencyLevelPolicy = consistencyLevelPolicy;
    }
}
