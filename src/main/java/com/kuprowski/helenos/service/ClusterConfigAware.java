package com.kuprowski.helenos.service;

import me.prettyprint.hector.api.Cluster;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface ClusterConfigAware {

    void setNewCluster(Cluster cluster);
}
