package tomekkup.helenos.dao.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Cluster;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Entity
@Table(name = "CLUSTERCONFIG")
public class ClusterConfiguration extends AbstractEntity {

    private String hosts;
    private String clusterName;
    private boolean active;
    private String alias;
    private BasicCredentials credentials;

    public ClusterConfiguration() {
    }

    public ClusterConfiguration(String alias, String hosts, String clusterName, boolean active) {
        setAlias(alias);
        setHosts(hosts);
        setClusterName(clusterName);
        setActive(active);
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Column(name = "ALIAS", nullable = false, length=32)
    public String getAlias() {
        return alias;
    }

    @Column(name = "HOSTS", nullable = false, length=32, unique=true)
    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    @Column(name = "CLUSTERNAME", nullable = false, length=32, unique=true)
    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Column(name = "ACTIVE", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Cluster createCluster() {
        return isCredentialsNotEmpty() ? new ThriftCluster(clusterName, new CassandraHostConfigurator(hosts), getCredentialsAsMap()) 
                                        : new ThriftCluster(clusterName, new CassandraHostConfigurator(hosts));
    }
    
    @Transient
    private Map<String, String> getCredentialsAsMap() {
        Map<String, String> credentialsMap = new HashMap<String, String>(2);
        credentialsMap.put("username", getCredentials().getUsername());
        credentialsMap.put("password", getCredentials().getPassword());
        return credentialsMap;
    }
    
    @Transient
    public boolean isCredentialsNotEmpty() {
        return StringUtils.hasText(getCredentials().getUsername()) && StringUtils.hasText(getCredentials().getPassword());
    }

    @Embedded
    public BasicCredentials getCredentials() {
        if (credentials == null) {
            credentials = new BasicCredentials();
        }
        return credentials;
    }

    public void setCredentials(BasicCredentials credentials) {
        this.credentials = credentials;
    }
}
