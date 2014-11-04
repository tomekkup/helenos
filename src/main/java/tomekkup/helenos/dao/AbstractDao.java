package tomekkup.helenos.dao;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public abstract class AbstractDao {

    @Autowired
    protected Properties queriesProperties;
    
    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Required
    public void setQueriesProperties(Properties queriesProperties) {
        this.queriesProperties = queriesProperties;
    }
}
