package tomekkup.helenos.service.impl;

import tomekkup.helenos.dao.AccountDao;
import tomekkup.helenos.dao.model.Account;
import tomekkup.helenos.service.AccountsProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public List<Account> loadAll() {
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
    public void store(Account account) {
        accountDao.store(account);
    }
    
    @Override
    public Account loadUserByUsername(String username) {
        return (Account) accountDao.loadUserByUsername(username);
    }
}
