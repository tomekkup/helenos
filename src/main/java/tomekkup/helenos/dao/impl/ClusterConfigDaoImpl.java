package tomekkup.helenos.dao.impl;

import java.io.Serializable;
import tomekkup.helenos.dao.ClusterConfigDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tomekkup.helenos.dao.model.ClusterConfiguration;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Component("clusterConfigDao")
@Transactional(propagation = Propagation.REQUIRED)
public class ClusterConfigDaoImpl extends AbstractDao implements ClusterConfigDao {
    private static final String HASTEXT_ERROR_MSG = "%s must be set in default.properties file";
    
    private @Value("${default.host}") String defaultHost;
    private @Value("${default.cluster.name}") String defaultClusterName;

    @Override
    public void setDefaultClusterName(String defaultClusterName) {
        this.defaultClusterName = defaultClusterName;
    }

    @Override
    public void setDefaultHost(String defaultHost) {
        this.defaultHost = defaultHost;
    }

    @Override
    public ClusterConfiguration get(String alias) {
        Session session = openSession();
        return get(session, alias);
    }
    
    private ClusterConfiguration get(Session session, String alias) {
        return (ClusterConfiguration) session.createCriteria(ClusterConfiguration.class).add(Restrictions.eq("alias", alias)).uniqueResult();
    }
    
    @Override
    public List<ClusterConfiguration> loadAll() {
        return getHibernateTemplate().loadAll(ClusterConfiguration.class);
    }

    @Override
    public long getConnectionsCount() {
        Session session = openSession();
        return ((Number) session.createCriteria(ClusterConfiguration.class).setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }
    
    @Override
    public void delete(String alias) {
        Session session = openSession();
        ClusterConfiguration cc = get(session, alias);
        session.delete(cc);
    }
    
    @Override
    protected void initDao() throws Exception {
        Assert.hasText(defaultHost, String.format(HASTEXT_ERROR_MSG, "default.host"));
        Assert.hasText(defaultClusterName, String.format(HASTEXT_ERROR_MSG, "default.cluster.name"));
    }
    
    private Serializable createDefaultConfiguration(Session session) {
        ClusterConfiguration configuration = new ClusterConfiguration("default", defaultHost, defaultClusterName, true);
        return session.save(configuration);
    }
    
    @Override
    public ClusterConfiguration getActive() {
        Session session = openSession();
        List<ClusterConfiguration> configuration = session.createCriteria(ClusterConfiguration.class).add(Restrictions.eq("active", true)).list();
        
        //tutaj zrobic inaczej
        //jesli defaultConn nie jest pusty to dodaj config nadpisujac
        if (CollectionUtils.isEmpty(configuration)) {
            Serializable configId = this.createDefaultConfiguration(session);
            return (ClusterConfiguration) session.load(ClusterConfiguration.class, configId);
        }

        return null;
    }

     private ClusterConfiguration getActiveIfExists(Session session) {
        List<ClusterConfiguration> list = session.createCriteria(ClusterConfiguration.class).add(Restrictions.eq("active", true)).list();
        
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
     
    @Override
    public void store(ClusterConfiguration configuration) {
        Session session = openSession();
        if (configuration.isActive()) {
            // wez aktywne i zdisabluj
            ClusterConfiguration active = getActiveIfExists(session);
            if (active != null) {
                active.setActive(false);
                session.update(active);
            }
        }
        session.saveOrUpdate(configuration);
    }
}
