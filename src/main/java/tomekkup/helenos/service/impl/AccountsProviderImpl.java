package tomekkup.helenos.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tomekkup.helenos.dao.AccountDao;
import static tomekkup.helenos.dao.impl.AccountDaoImpl.ADMIN;
import static tomekkup.helenos.dao.impl.AccountDaoImpl.ROLE_ADMIN;
import tomekkup.helenos.dao.model.Account;
import tomekkup.helenos.dao.model.Authority;
import tomekkup.helenos.dao.model.qx.QxAccount;
import tomekkup.helenos.service.AccountsProvider;
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
@Component("accountsProvider")
public class AccountsProviderImpl implements AccountsProvider, InitializingBean {

    private AccountDao accountDao;

    public AccountsProviderImpl() {
        super();
    }

    @Autowired(required = true)
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<QxAccount> loadAll() {
        List<Account> accounts = accountDao.loadAll();
        final List<QxAccount> ret = new ArrayList<QxAccount>();
        CollectionUtils.forAllDo(accounts, new Closure() {

            @Override
            public void execute(Object input) {
                Account account = (Account) input;
                QxAccount qxAcc = new QxAccount(account.getUsername(), "", account.getAuthorities().iterator().next(), account.isEnabled());
                ret.add(qxAcc);
            }
        });
        return ret;
    }

    @Override
    public long getAccountsCount() {
        return accountDao.getCount();
    }

    @Override
    public void delete(String username) {
        if (username.toLowerCase().equals(ADMIN)) {
            throw new IllegalArgumentException("admin account can not be removed");
        }
        Account entity = accountDao.load(username);
        accountDao.delete(entity);
    }

    @Override
    public void saveNewPassword(QxPasswordChangeRequest pcr) throws IllegalStateException {
        if (!pcr.getPassword1().equals(pcr.getPassword2())) {
            throw new IllegalStateException("both passwords must equal");
        }
        if (!StringUtils.hasText(pcr.getPassword2())) {
            throw new IllegalStateException("password can not be empty");
        }

        Account entity = accountDao.load(pcr.getUsername());
        entity.setPassword(pcr.getPassword1());
        accountDao.encodePasswd(entity);
        accountDao.saveOrUpdate(entity);
    }

    @Override
    public void createAccount(QxAccount account) throws IllegalStateException {
        Account entity = accountDao.get(account.getUsername());
        if (entity != null) {
            throw new IllegalStateException("account with this name already exists");
        }
        Authority authority = new Authority(account.getAuthorities().iterator().next());
        //authorityDao.persist(authority);
        entity = new Account(account.getUsername(), account.getPassword(), authority, account.isEnabled());
        accountDao.encodePasswd(entity);
        accountDao.saveOrUpdate(entity);
    }

    @Override
    public QxAccount loadUserByUsername(String username) {
        Account account = accountDao.load(username);
        if (account == null) {
            throw new IllegalStateException("account not exists");
        }
        QxAccount qxAcc = new QxAccount(account.getUsername(), "", account.getAuthorities().iterator().next(), account.isEnabled());
        return qxAcc;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ensureDefaultCreds();
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void ensureDefaultCreds() {
        try {
            accountDao.loadUserByUsername(ADMIN);
        } catch (UsernameNotFoundException exc) {
            String encodedPasswd = accountDao.encodePasswd(ADMIN, ADMIN);
            Account user = new Account(ADMIN, encodedPasswd, null, true);
            Authority authority = new Authority(ROLE_ADMIN);
            user.addAuthority(authority);
            
            accountDao.persist(user);
        }
    }
}
