package tomekkup.helenos.context;

import tomekkup.helenos.dao.model.ClusterConfiguration;
import tomekkup.helenos.dao.ClusterConfigDao;
import tomekkup.helenos.service.ClusterConfigAware;
import java.util.Map;
import me.prettyprint.hector.api.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class PostConfiguringClusterListener implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired
    private ClusterConfigDao clusterConfigDao;
    
    public PostConfiguringClusterListener() {
    }

    @Required
    public void setClusterConfigDao(ClusterConfigDao clusterConfigDao) {
        this.clusterConfigDao = clusterConfigDao;
    }
    
    private boolean isReady(ContextRefreshedEvent event) {
        XmlWebApplicationContext context = (XmlWebApplicationContext) event.getSource();
        return context.getServletConfig() != null;
    }
    
    public static void propagadeConfigChanges(ApplicationContext applicationContext, Cluster cluster) {
        Map<String, ClusterConfigAware> beans = applicationContext.getBeansOfType(ClusterConfigAware.class);
        for (ClusterConfigAware bean : beans.values()) {
            bean.setNewCluster(cluster);
        }
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isReady(event)) {
            ApplicationContext applicationContext = event.getApplicationContext();
            ClusterConfiguration configuration = clusterConfigDao.getActive();
            
            propagadeConfigChanges(applicationContext.getParent(), configuration.createCluster());
        }
    }
}
