package tomekkup.helenos.types.qx;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class QxClusterConfiguration {
    
    private String hosts;
    private String clusterName;
    private boolean active;
    private String alias;
    private QxBasicCredentials credentials;

    public QxClusterConfiguration() {
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public QxBasicCredentials getCredentials() {
        if (credentials == null) {
            credentials = new QxBasicCredentials();
        }
        return credentials;
    }

    public void setCredentials(QxBasicCredentials credentials) {
        this.credentials = credentials;
    }
}
