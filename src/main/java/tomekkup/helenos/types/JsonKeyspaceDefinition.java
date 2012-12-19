package tomekkup.helenos.types;

import java.util.List;
import java.util.Map;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class JsonKeyspaceDefinition {
    
    public JsonKeyspaceDefinition() {
        
    }
    
    private String name;
    private String strategyClass;
    private Map<String, String> strategyOptions;
    private List<JsonColumnFamilyDefinition> cfDefs;
    private int replicationFactor;

    public JsonKeyspaceDefinition(KeyspaceDefinition kd) {
        this.name = kd.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrategyClass() {
        return strategyClass;
    }

    public void setStrategyClass(String strategyClass) {
        this.strategyClass = strategyClass;
    }

    public Map<String, String> getStrategyOptions() {
        return strategyOptions;
    }

    public void setStrategyOptions(Map<String, String> strategyOptions) {
        this.strategyOptions = strategyOptions;
    }

    public List<JsonColumnFamilyDefinition> getCfDefs() {
        return cfDefs;
    }

    public void setCfDefs(List<JsonColumnFamilyDefinition> cfDefs) {
        this.cfDefs = cfDefs;
    }

    public int getReplicationFactor() {
        return replicationFactor;
    }

    public void setReplicationFactor(int replicationFactor) {
        this.replicationFactor = replicationFactor;
    }
    
    
    
}
