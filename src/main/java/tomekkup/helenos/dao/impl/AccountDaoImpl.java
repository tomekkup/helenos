package tomekkup.helenos.dao.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tomekkup.helenos.dao.AccountDao;
import tomekkup.helenos.dao.model.Account;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Repository("accountDao")
@Transactional
public class AccountDaoImpl extends AbstractDao<Account, String> implements AccountDao {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ADMIN = "admin";
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SaltSource saltSource;

    @Autowired
    public AccountDaoImpl(@Value("tomekkup.helenos.dao.model.Account") Class<Account> clazz) {
        super(clazz);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session session = openSession();
        return loadUserByUsername(session, username);
    }

    private UserDetails loadUserByUsername(Session session, String username) throws UsernameNotFoundException {
        UserDetails entity = get(session, username);
        
        if (entity == null) {
            throw new UsernameNotFoundException(String.format("user '%s' not found", username));
        }
        return entity;
    }

    @Override
    public String encodePasswd(String username, String passwd) {
        return passwordEncoder.encodePassword(passwd, saltSource.getSalt(new Account(username)));
    }

    @Override
    public void encodePasswd(Account user) {
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), saltSource.getSalt(new Account(user.getUsername()))));
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
