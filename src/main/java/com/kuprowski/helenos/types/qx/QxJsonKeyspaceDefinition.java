package com.kuprowski.helenos.types.qx;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class QxJsonKeyspaceDefinition {

    public QxJsonKeyspaceDefinition() {
        
    }
    
    private String keyspaceName;
    private int replicationFactor;
    private String strategyClass;

    public String getKeyspaceName() {
        return keyspaceName;
    }

    public void setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
    }

    public int getReplicationFactor() {
        return replicationFactor;
    }

    public void setReplicationFactor(int replicationFactor) {
        this.replicationFactor = replicationFactor;
    }

    public String getStrategyClass() {
        return strategyClass;
    }

    public void setStrategyClass(String strategyClass) {
        this.strategyClass = strategyClass;
    }
}
