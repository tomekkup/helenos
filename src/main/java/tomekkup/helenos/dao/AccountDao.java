package tomekkup.helenos.dao;

import tomekkup.helenos.dao.model.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
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
@Component("accountDao")
public class AccountDao extends AbstractDao implements UserDetailsService, InitializingBean {
    
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SaltSource saltSource;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            userDetails = jdbcTemplate.queryForObject(queriesProperties.getProperty("user.get.by.username"), new MapSqlParameterSource("username", username), new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(String.format("user '%s' not found", username));
        }
        
        return userDetails;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        this.ensureDefaultCreds();
    }
    
    private void ensureDefaultCreds() {
        try {
            loadUserByUsername("admin");
        } catch (UsernameNotFoundException e) {
            String encodedPasswd = passwordEncoder.encodePassword("admin", saltSource.getSalt(new Account("admin")));
            Account user = new Account("admin", encodedPasswd, new SimpleGrantedAuthority(ROLE_ADMIN), true);
            user.addAuthority(new SimpleGrantedAuthority(ROLE_USER));
            
            store(user);
        }
    }
    
    public void store(Account user) {
        jdbcTemplate.update(queriesProperties.getProperty("user.merge"), prepareParameterSource(user));
    }
    
    public List<Account> loadAll() {
        return jdbcTemplate.query(queriesProperties.getProperty("user.select.star"), new MapSqlParameterSource(), new UserMapper());
    }
    
    public long getAccountsCount() {
        return jdbcTemplate.queryForLong(queriesProperties.getProperty("user.count"), new HashMap<String, Object>());
    }
    
    public void delete(String username) {
        jdbcTemplate.update(queriesProperties.getProperty("user.delete"), new MapSqlParameterSource("username", username));
    }
    
    private SqlParameterSource prepareParameterSource(Account user) {
        return new MapSqlParameterSource(user.toParametersMap());
    }
    
    private static final class UserMapper implements RowMapper<Account> {
        
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account user = new Account();
            user.setUsername(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setEnabled(rs.getBoolean("ENABLED"));
            
            String[] tempAuths = StringUtils.commaDelimitedListToStringArray(rs.getString("AUTHORITIES"));
            Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
            for (String ta : tempAuths) {
                authorities.add(new SimpleGrantedAuthority(ta));
            }
            user.setAuthorities(authorities);
            return user;
        }
    }
    
    @Required
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Required
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }
}
