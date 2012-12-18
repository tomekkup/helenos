package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.dao.AccountDao;
import com.kuprowski.helenos.dao.model.Account;
import com.kuprowski.helenos.service.AccountsProvider;
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
}
