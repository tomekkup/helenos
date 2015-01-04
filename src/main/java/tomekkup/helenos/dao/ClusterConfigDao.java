package tomekkup.helenos.dao;

import org.hibernate.Session;
import tomekkup.helenos.dao.model.ClusterConfiguration;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface ClusterConfigDao extends EntityDao<ClusterConfiguration, Long> {
    
    ClusterConfiguration getByAlias(String alias);
    
    ClusterConfiguration getByAlias(Session session, String alias);

    ClusterConfiguration getActive();

    void setDefaultClusterName(String defaultClusterName);

    void setDefaultHost(String defaultHost);
}
