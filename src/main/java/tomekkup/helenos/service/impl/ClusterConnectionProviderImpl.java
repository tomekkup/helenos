package tomekkup.helenos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.CassandraHost;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import tomekkup.helenos.context.PostConfiguringClusterListener;
import tomekkup.helenos.dao.ClusterConfigDao;
import tomekkup.helenos.dao.model.BasicCredentials;
import tomekkup.helenos.dao.model.ClusterConfiguration;
import tomekkup.helenos.service.ClusterConfigAware;
import tomekkup.helenos.service.ClusterConnectionProvider;
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
@Component("clusterConnectionProvider")
public class ClusterConnectionProviderImpl extends AbstractProvider implements ClusterConnectionProvider, ApplicationContextAware, ClusterConfigAware {

    @Autowired
    protected ClusterConfigDao clusterConfigDao;
    private ApplicationContext applicationContext;

    @Required
    public void setClusterConfigDao(ClusterConfigDao clusterConfigDao) {
        this.clusterConfigDao = clusterConfigDao;
    }

    @Override
    public List<ClusterConfiguration> loadAll() {
        return clusterConfigDao.loadAll();
    }

    @Override
    public long getConnectionsCount() {
        return clusterConfigDao.getCount();
    }

    @Override
    public void create(QxClusterConfiguration configuration) {
        ClusterConfiguration entity = clusterConfigDao.getByAlias(configuration.getAlias());
        if (entity != null) {
            throw new IllegalStateException("Connection already exists");
        }
        
        entity = new ClusterConfiguration();
        entity.setCredentials(new BasicCredentials());
        entity.setAlias(configuration.getAlias());
        entity.setClusterName(configuration.getClusterName());
        entity.setHosts(configuration.getHosts());
        if (configuration.getCredentials() != null) {
            entity.getCredentials().setUsername(configuration.getCredentials().getUsername());
            entity.getCredentials().setPassword(configuration.getCredentials().getPassword());
        }
        
        clusterConfigDao.saveOrUpdate(entity);
    }
    
    @Override
    public void store(QxClusterConfiguration configuration) {
        ClusterConfiguration entity = clusterConfigDao.getByAlias(configuration.getAlias());
        if (entity == null) {
            throw new IllegalStateException("Connection not exists");
        }
        
        entity.setClusterName(configuration.getClusterName());
        entity.setHosts(configuration.getHosts());
        if (configuration.getCredentials() != null) {
            entity.getCredentials().setUsername(configuration.getCredentials().getUsername());
            entity.getCredentials().setPassword(configuration.getCredentials().getPassword());
        }
        
        clusterConfigDao.saveOrUpdate(entity);
    }

    @Override
    public ClusterConfiguration getConnectionByAlias(String alias) {
        return clusterConfigDao.getByAlias(alias);
    }

    @Override
    public void activate(String alias) {
        if (cluster != null) {
            cluster.getConnectionManager().shutdown();
            cluster = null;
        }
        
        ClusterConfiguration activeConf = clusterConfigDao.getActive();
        activeConf.setActive(false);
        clusterConfigDao.saveOrUpdate(activeConf);
        
        ClusterConfiguration configuration = clusterConfigDao.getByAlias(alias);
        if (!configuration.isActive()) {
            configuration.setActive(true);
            clusterConfigDao.saveOrUpdate(configuration);
        }
        PostConfiguringClusterListener.propagadeConfigChanges(applicationContext, configuration.createCluster());
    }

    @Override
    public void delete(String alias) {
        ClusterConfiguration entity = clusterConfigDao.getByAlias(alias);
        clusterConfigDao.delete(entity);
    }

    @Override
    public Map<String, Set<CassandraHost>> getConnectionStatus() {
        HConnectionManager connectionManager = cluster.getConnectionManager();

        Map<String, Set<CassandraHost>> ret = new HashMap<String, Set<CassandraHost>>(3);

        ret.put("LIVE", connectionManager.getHosts());
        ret.put("DOWN", connectionManager.getDownedHosts());
        ret.put("SUSPENDED", connectionManager.getSuspendedCassandraHosts());

        return ret;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
