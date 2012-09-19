package com.kuprowski.helenos.jdbc.core.support;

import com.kuprowski.helenos.ClusterConfiguration;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.CollectionUtils;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class ClusterConfigDao {

    private Properties queries;
    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Required
    public void setQueries(Properties queries) {
        this.queries = queries;
    }

    public ClusterConfiguration getActive() {
        List<ClusterConfiguration> configuration = jdbcTemplate.query(queries.getProperty("clusterconfig.select.star.wa"), new MapSqlParameterSource("active", true), new ClusterConfigurationMapper());

        if (CollectionUtils.isEmpty(configuration)) {
            this.createDefaultConfiguration();
            return this.getActive();
        }

        return configuration.get(0);
    }

    public List<ClusterConfiguration> loadAll() {
        return jdbcTemplate.query(queries.getProperty("clusterconfig.select.star"), new MapSqlParameterSource(), new ClusterConfigurationMapper());
    }

    private void createDefaultConfiguration() {
        jdbcTemplate.update(queries.getProperty("clusterconfig.insert"), prepareParameterSource(new ClusterConfiguration("default", "localhost:9160", "TestCluster", true)));
    }

    private SqlParameterSource prepareParameterSource(ClusterConfiguration configuration) {
        return new MapSqlParameterSource(configuration.toParametersMap());
    }

    public void store(ClusterConfiguration configuration) {
        if (configuration.isActive()) {
            jdbcTemplate.update(queries.getProperty("clusterconfig.update.all.active.false"), new MapSqlParameterSource());
        }
        jdbcTemplate.update(queries.getProperty("clusterconfig.merge"), prepareParameterSource(configuration));
    }

    public void delete(String alias) {
        jdbcTemplate.update(queries.getProperty("clusterconfig.delete"), new MapSqlParameterSource("alias", alias));
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
