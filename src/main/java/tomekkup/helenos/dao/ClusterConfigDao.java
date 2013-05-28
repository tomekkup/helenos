package tomekkup.helenos.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tomekkup.helenos.ClusterConfiguration;

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
public class ClusterConfigDao extends AbstractDao {

    public ClusterConfiguration get(String alias) {
        return jdbcTemplate.queryForObject(queriesProperties.getProperty("clusterconfig.get.by.alias"), new MapSqlParameterSource("alias", alias), new ClusterConfigurationMapper());
    }

    public ClusterConfiguration getActive() {
        List<ClusterConfiguration> configuration = jdbcTemplate.query(queriesProperties.getProperty("clusterconfig.select.star.wa"), new MapSqlParameterSource("active", true), new ClusterConfigurationMapper());

        if (CollectionUtils.isEmpty(configuration)) {
            this.createDefaultConfiguration();
            return this.getActive();
        }

        return configuration.get(0);
    }

    public List<ClusterConfiguration> loadAll() {
        return jdbcTemplate.query(queriesProperties.getProperty("clusterconfig.select.star"), new MapSqlParameterSource(), new ClusterConfigurationMapper());
    }

    public long getConnectionsCount() {
        return jdbcTemplate.queryForLong(queriesProperties.getProperty("clusterconfig.count"), new HashMap<String, Object>());
    }

    private void createDefaultConfiguration() {
        jdbcTemplate.update(queriesProperties.getProperty("clusterconfig.insert"), prepareParameterSource(new ClusterConfiguration("default", "localhost:9160", "TestCluster", true)));
    }

    private SqlParameterSource prepareParameterSource(ClusterConfiguration configuration) {
        return new MapSqlParameterSource(configuration.toParametersMap());
    }

    public void store(ClusterConfiguration configuration) {
        if (configuration.isActive()) {
            jdbcTemplate.update(queriesProperties.getProperty("clusterconfig.update.all.active.false"), new MapSqlParameterSource());
        }
        jdbcTemplate.update(queriesProperties.getProperty("clusterconfig.merge"), prepareParameterSource(configuration));
    }

    public void delete(String alias) {
        jdbcTemplate.update(queriesProperties.getProperty("clusterconfig.delete"), new MapSqlParameterSource("alias", alias));
    }

    private static final class ClusterConfigurationMapper implements RowMapper<ClusterConfiguration> {

        @Override
        public ClusterConfiguration mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClusterConfiguration cc = new ClusterConfiguration();
            cc.setAlias(rs.getString("ALIAS"));
            cc.setHosts(rs.getString("HOSTS"));
            cc.setClusterName(rs.getString("CLUSTERNAME"));
            cc.setActive(rs.getBoolean("ACTIVE"));
            return cc;
        }
    }
}
