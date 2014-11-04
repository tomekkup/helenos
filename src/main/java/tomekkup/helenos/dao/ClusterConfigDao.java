/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tomekkup.helenos.dao;

import java.util.List;
import tomekkup.helenos.dao.model.ClusterConfiguration;

/**
 *
 * @author developer
 */
public interface ClusterConfigDao {

    void delete(String alias);

    ClusterConfiguration get(String alias);

    ClusterConfiguration getActive();

    long getConnectionsCount();

    List<ClusterConfiguration> loadAll();

    void setDefaultClusterName(String defaultClusterName);

    void setDefaultHost(String defaultHost);

    void store(ClusterConfiguration configuration);
    
}
