package tomekkup.helenos.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tomekkup.helenos.dao.AccountDao;
import tomekkup.helenos.dao.model.Authority;
import tomekkup.helenos.dao.model.qx.QxAccount;
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
@Repository("accountDao")
@Transactional
public class AccountDaoImpl extends AbstractDao implements AccountDao {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ADMIN = "admin";
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SaltSource saltSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session session = openSession();
        return loadUserByUsername(session, username);
    }

    private UserDetails loadUserByUsername(Session session, String username) throws UsernameNotFoundException {
        UserDetails userDetails = (UserDetails) session.createCriteria(QxAccount.class).add(Restrictions.eq("username", username)).uniqueResult();
        if (userDetails == null) {
            throw new UsernameNotFoundException(String.format("user '%s' not found", username));
        }

        return userDetails;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void ensureDefaultCreds() {
        Session session = openSession();
        try {
            loadUserByUsername(session, ADMIN);
        } catch (UsernameNotFoundException exc) {
            String encodedPasswd = encodePasswd(ADMIN, ADMIN);
            QxAccount user = new QxAccount(ADMIN, encodedPasswd, null, true);
            Authority authority = new Authority(user, ROLE_USER);
            user.addAuthority(authority);
            store(session, user);
            session.persist(authority);
        }
    }

    private String encodePasswd(String username, String passwd) {
        return passwordEncoder.encodePassword(passwd, saltSource.getSalt(new QxAccount(username)));
    }

    private void encodePasswd(QxAccount user) {
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), saltSource.getSalt(new QxAccount(user.getUsername()))));
    }

    @Override
    public void createAccount(QxAccount user) {
        this.encodePasswd(user);
        store(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void store(QxAccount user) {
        Session session = openSession();
        store(session, user);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    private void store(Session session, QxAccount user) {
        session.persist(user);
    }

    @Override
    public void saveNewPassword(QxPasswordChangeRequest pcr) throws IllegalStateException {
        if (!pcr.getPassword1().equals(pcr.getPassword2())) {
            throw new IllegalStateException("both passwords must equal");
        }
        if (!StringUtils.hasText(pcr.getPassword2())) {
            throw new IllegalStateException("password can not be empty");
        }
        Session session = openSession();
        QxAccount account = (QxAccount) loadUserByUsername(session, pcr.getUsername());
        account.setPassword(encodePasswd(pcr.getUsername(), pcr.getPassword1()));
        store(session, account);
    }

    @Override
    public List<QxAccount> loadAll() {
        return getHibernateTemplate().loadAll(QxAccount.class);
    }

    @Override
    public long getAccountsCount() {
        return ((Number) getSession().createCriteria(QxAccount.class).setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }

    @Override
    public void delete(String username) {
        if (username.toLowerCase().equals(ADMIN)) {
            throw new IllegalArgumentException("admin account can not be removed");
        }
        
        Session session = openSession();
        QxAccount account = (QxAccount)loadUserByUsername(session, username);
        session.delete(account);
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
