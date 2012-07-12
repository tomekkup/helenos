/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.service;

import com.kuprowski.helenos.types.JsonKeyspaceDefinition;
import java.util.ArrayList;
import java.util.List;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import org.springframework.stereotype.Component;

/**
 *
 * @author tomek
 */
@Component("clusterProvider")
public class ClusterProviderImpl extends AbstractProvider implements ClusterProvider {
    
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
}
