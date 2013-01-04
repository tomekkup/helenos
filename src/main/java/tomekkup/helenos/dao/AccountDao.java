package tomekkup.helenos.dao;

import tomekkup.helenos.types.qx.QxAccount;
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
import tomekkup.helenos.types.qx.QxPasswordChangeRequest;

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
            String encodedPasswd = encodePasswd("admin", "admin");
            QxAccount user = new QxAccount("admin", encodedPasswd, new SimpleGrantedAuthority(ROLE_ADMIN), true);
            user.addAuthority(new SimpleGrantedAuthority(ROLE_USER));
            
            store(user);
        }
    }
    
    private String encodePasswd(String username, String passwd) {
        return passwordEncoder.encodePassword(passwd, saltSource.getSalt(new QxAccount(username)));
    }
    
    private void encodePasswd(QxAccount user) {
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), saltSource.getSalt(new QxAccount(user.getUsername()))));
    }
    
    public void createAccount(QxAccount user) {
        encodePasswd(user);
        store(user);
    }
    
    public void store(QxAccount user) {
        jdbcTemplate.update(queriesProperties.getProperty("user.merge"), prepareParameterSource(user));
    }
    
    public void saveNewPassword(QxPasswordChangeRequest pcr) throws IllegalStateException {
        if(!pcr.getPassword1().equals(pcr.getPassword2())) {
            throw new IllegalStateException("both passwords must equal");
        }
        if(!StringUtils.hasText(pcr.getPassword2())) {
            throw new IllegalStateException("password can not be empty");
        }
        QxAccount account = (QxAccount)loadUserByUsername(pcr.getUsername());
        account.setPassword(encodePasswd(pcr.getUsername(), pcr.getPassword1()));
        this.store(account);
    }
    
    public List<QxAccount> loadAll() {
        return jdbcTemplate.query(queriesProperties.getProperty("user.select.star"), new MapSqlParameterSource(), new UserMapper());
    }
    
    public long getAccountsCount() {
        return jdbcTemplate.queryForLong(queriesProperties.getProperty("user.count"), new HashMap<String, Object>());
    }
    
    public void delete(String username) {
        if (username.toLowerCase().equals("admin")) {
            throw new IllegalArgumentException("admin account can not be removed");
        }
        jdbcTemplate.update(queriesProperties.getProperty("user.delete"), new MapSqlParameterSource("username", username));
    }
    
    private SqlParameterSource prepareParameterSource(QxAccount user) {
        return new MapSqlParameterSource(user.toParametersMap());
    }
    
    private static final class UserMapper implements RowMapper<QxAccount> {
        
        @Override
        public QxAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
            QxAccount user = new QxAccount();
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
