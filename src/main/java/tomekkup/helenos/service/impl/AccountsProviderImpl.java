package tomekkup.helenos.service.impl;

import tomekkup.helenos.dao.AccountDao;
import tomekkup.helenos.types.qx.QxAccount;
import tomekkup.helenos.service.AccountsProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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
public class AccountsProviderImpl implements AccountsProvider {

    @Autowired
    private AccountDao accountDao;

    public AccountsProviderImpl() {
        super();
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<QxAccount> loadAll() {
        return accountDao.loadAll();
    }

    @Override
    public long getAccountsCount() {
        return accountDao.getAccountsCount();
    }
    
    @Override
    public void delete(String username) {
        accountDao.delete(username);
    }
    
    @Override
    public void store(QxAccount account) {
        accountDao.store(account);
    }
    
    @Override
    public void saveNewPassword(QxPasswordChangeRequest pcr) throws IllegalStateException {
        accountDao.saveNewPassword(pcr);
    }
    
    @Override
    public void createAccount(QxAccount account) throws IllegalStateException {
        try {
            loadUserByUsername(account.getUsername());
        } catch(UsernameNotFoundException e) {
            accountDao.createAccount(account);
            return;
        }
        throw new IllegalStateException("account with this name already exists");
    }
    
    @Override
    public QxAccount loadUserByUsername(String username) {
        return (QxAccount) accountDao.loadUserByUsername(username);
    }
}
